package com.king.util.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.king.util.log.LogUtil;
import com.king.util.log.annotation.SystemControllerLog;
import com.king.util.log.annotation.SystemServiceLog;
import com.king.util.log.vo.LogData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @description: 日志切点类
 **/
@Aspect
@Component
@Order(1)
public class SystemLogAspect {
    private static final ThreadLocal<LogData> logThreadLocal=new NamedThreadLocal<>("ThreadLocal LogData");
    @Autowired(required=false)
    HttpServletRequest request;
    @Autowired(required=false)
    HttpServletResponse response;

    @Pointcut(value = "@annotation(com.king.util.log.annotation.SystemServiceLog)")
    public void serviceAspect() {}

    @Pointcut(value = "@annotation(com.king.util.log.annotation.SystemControllerLog)")
    public void controllerAspect() {}

    /**
     * Controller 切面前置
     * @param joinPoint
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        long beginTime = System.currentTimeMillis();//得到线程绑定的局部变量（开始时间）
        String title="";
        String remoteAddr=getIpAddr(request);//请求的IP
        String requestUri=request.getRequestURI();//请求的Uri
        String method=request.getMethod();        //请求的方法类型(post/get)
        try {
            title=getControllerMethodDescription(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 打印JVM信息。
        LogData log = new LogData();
        log.setUuid(Thread.currentThread().getId()+"");
        log.setBeginTime(beginTime);
        log.setClassName(joinPoint.getTarget().getClass().getName());
        log.setFreeMemory(Runtime.getRuntime().freeMemory());
        log.setMaxMemory(Runtime.getRuntime().maxMemory());
        log.setMethod(method);
        log.setMethodDesc(title);
        log.setMethodName(joinPoint.getSignature().getName());
        log.setRemoteAddr(remoteAddr);
        log.setRequestUri(requestUri);
        log.setTotalMemory(Runtime.getRuntime().totalMemory());
        logThreadLocal.set(log);
    }

    @AfterReturning(value = "controllerAspect()", returning = "rtv")
    public void aterReturning(JoinPoint joinPoint,Object rtv) {
        long endTime = System.currentTimeMillis();//得到线程绑定的局部变量（结束时间）
        LogData log =  logThreadLocal.get();
        Map<String,String[]> params=request.getParameterMap(); //请求提交的参数
        log.setParams(JSONObject.toJSONString(params));
        log.setOutData(JSONObject.toJSONString(rtv));
        log.setEndTime(endTime);
        LogUtil.info("Controller拦截监听",
                "请求唯一标识：{} 方法描述：{} 类地址：{} 请求地址：{} URI: {} 请求类型：{} 计时开始：{} 计时结束：{}   耗时： {}ms  传入参数：{} 传出参数：{} 最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                log.getUuid(),
                log.getMethodDesc(),
                log.getClassName()+"."+log.getMethodName(),
                log.getRemoteAddr(),
                log.getRequestUri(),
                log.getMethod(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(log.getBeginTime()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(log.getEndTime()),
                log.getEndTime() - log.getBeginTime(),
                log.getParams(),
                log.getOutData(),
                log.getMaxMemory()/1024/1024,
                log.getTotalMemory()/1024/1024,
                log.getFreeMemory()/1024/1024,
                ((log.getMaxMemory()-log.getTotalMemory()+log.getFreeMemory())/1024/1024)
        );
    }

    @Around("serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        LogData log = logThreadLocal.get();
        String uuid =null;
        if(log !=null){
            uuid =log.getUuid();
        }else{
            log =new LogData();
            log.setUuid(Thread.currentThread().getId()+"");;
            logThreadLocal.set(log);
        }
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String title="";
        try {
            title=getServiceMthodDescription(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] params = joinPoint.getArgs();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        LogUtil.info("Service拦截监听",
                "请求唯一标识：{} 方法描述：{} 类地址：{} 计时开始：{} 计时结束：{}   耗时： {}ms  传入参数：{} 传出参数：{} 最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                uuid,
                title,
                className+"."+methodName,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(beginTime),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                endTime - beginTime,
                JSONObject.toJSONString(params),
                JSONObject.toJSONString(result),
                Runtime.getRuntime().maxMemory()/1024/1024,
                Runtime.getRuntime().totalMemory()/1024/1024,
                Runtime.getRuntime().freeMemory()/1024/1024,
                ((Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024)
        );
        return result;
    }
    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
