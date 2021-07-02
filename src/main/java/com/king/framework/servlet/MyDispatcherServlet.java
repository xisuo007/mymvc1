package com.king.framework.servlet;

import com.king.framework.annotation.MYController;
import com.king.framework.annotation.MYRequestMapping;
import com.king.framework.annotation.MYRequestParam;
import com.king.framework.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ljq on 2018/11/29 14:01
 */
public class MyDispatcherServlet extends HttpServlet {
    private static final String LOCATION = "contextConfigLocation";
    private List<Handler> handlerMapping = new ArrayList<>();
    private Map<Handler,HandlerAdapter> adapterMapping = new HashMap<>();
    private List<ViewResolver> viewResolvers = new ArrayList<>();



    //初始化IOC容器
    @Override
    public void init(ServletConfig config) throws ServletException {
        //IOC容器必须要先初始化
        MyApplicationContext context = new MyApplicationContext(config.getInitParameter(LOCATION));

        //请求解析
        initMultipartResolver(context);
        //多语言，国际化
        initLocaleResolver(context);
        //主题view层的
        initThemeResolver(context);

        //----------------
        //解析url和Method的关联关系
        initHandlerMappings(context);
        //适配器（匹配的过程）
        initHandlerAdapters(context);
        //----------

        //异常解析
        initHandlerExceptionResolvers(context);
        //试图转发（根据试图名字匹配到一个具体模板）
        initRequestToViewNameTranslator(context);
        //解析模板中的内容（拿到服务器传过来的数据，生成html代码）
        initViewResolvers(context);

        initFlashMapManager(context);

        System.out.println("Spring MVC is init");
    }
    //请求解析
    private void initMultipartResolver(MyApplicationContext context) {

    }
    //多语言国际化
    private void initLocaleResolver(MyApplicationContext context) {

    }
    //主题view层的
    private void initThemeResolver(MyApplicationContext context) {

    }
    //解析url和Method的关联关系
    private void initHandlerMappings(MyApplicationContext context) {
        Map<String,Object> ioc = context.getAll();
        if (ioc.isEmpty()) {
            return;
        }
        //只要是由Cotroller修饰类，里面方法全部找出来
        //这个方法上应该加了RequestMaping注解，如果没加这个注解，这个方法是不能被外界来访问的
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(MYController.class)) {
                continue;
            }
            String url = "";
            if (clazz.isAnnotationPresent(MYRequestMapping.class)) {
                MYRequestMapping mapping = clazz.getAnnotation(MYRequestMapping.class);
                url = mapping.value();
            }
            //扫描Controller下面的所有方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MYRequestMapping.class)) {
                    continue;
                }
                MYRequestMapping mapping = method.getAnnotation(MYRequestMapping.class);
                String regex = (url + mapping.value()).replaceAll("/+","/");
                Pattern compile = Pattern.compile(regex);
                handlerMapping.add(new Handler(entry.getValue(),method,compile));
                System.out.println("Mapping" + regex +" "+method.toString());
            }
        }
        //RequestMapping 会配置一个url，那么一个url就对应一个方法，并将这个关系保存到map中
    }
    //适配器（匹配过程）
    //主要是用来动态匹配我们参数的
    private void initHandlerAdapters(MyApplicationContext context) {
        if (handlerMapping.isEmpty()) {
            return;
        }
        //参数类型作为key，参数的索引号作为值
        Map<String,Integer> paramMapping = new HashMap<>();
        //只需要去除来具体的某个方法
        for (Handler handler : handlerMapping) {
            //把这个方法上面的所有的参数都拿到
            Class<?>[] parameterTypes = handler.method.getParameterTypes();
            //有顺序，但是通过反射，没法拿到我们的参数名字
            //匹配自定义参数列表
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(),i);
                }
            }
            //这里是匹配Request和Response
            Annotation[][] pa = handler.method.getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]) {
                    if (a instanceof MYRequestParam) {
                        String paramName = ((MYRequestParam) a).value();
                        if (!"".equals(paramName.trim())) {
                            paramMapping.put(paramName,i);
                        }
                    }
                }
            }
            adapterMapping.put(handler,new HandlerAdapter(paramMapping));
        }
    }
    //异常解析
    private void initHandlerExceptionResolvers(MyApplicationContext context) {

    }
    //视图转发（根据视图名字匹配到一个具体的模板）
    private void initRequestToViewNameTranslator(MyApplicationContext context) {

    }
    //解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
    private void initViewResolvers(MyApplicationContext context) {
        //模板一般是不会放到WebRoot下的，而是放到WEB-INF下，或者classes下
        //这样就避免了用户直接请求到模板
        //加载模板的个数，存储到缓存中
        //检查模板中的语法错误
        String templateRoot = context.getConfig().getProperty("templateRoot");
        //归根到底就是一个文件，普通文件
        String rootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File rootDir = new File(rootPath);
        for (File template : rootDir.listFiles()) {
            viewResolvers.add(new ViewResolver(template.getName(),template));
        }
    }

    private void initFlashMapManager(MyApplicationContext context) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        }catch (Exception e){
            resp.getWriter().write("500 Exception, Msg : " + Arrays.toString(e.getStackTrace()));
        }
    }

    private Handler getHandler(HttpServletRequest req){
        //循环handlerMapping
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handlerMapping) {
            Matcher matcher = handler.pattern.matcher(url);
            if (!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Handler handler){
        if (adapterMapping.isEmpty()) {
            return null;
        }
        return adapterMapping.get(handler);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            //先取出来一个Handler，从HandlerMapping取
            Handler handler = getHandler(req);
            if (handler == null) {
                resp.getWriter().write("404 Not Found");
                return;
            }
            //再取出来一个适配器
            //再由适配器调用我们具体的方法
            HandlerAdapter ha = getHandlerAdapter(handler);
            MyModelAndView mv = ha.handler(req, resp, handler);

            //写一个模板框架
            //Veloctiy  Freemark  jsp
            applyDefaultViewName(resp,mv);
        }catch (Exception e){
            throw e;
        }
    }

    public void applyDefaultViewName(HttpServletResponse resp,MyModelAndView mv) throws Exception{
        if (null == mv) {
            return;
        }
        if (viewResolvers.isEmpty()) {
            return;
        }
        for (ViewResolver resolver : viewResolvers) {
            if (!mv.getView().equals(resolver.getViewName())) {
                continue;
            }
            String r = resolver.parse(mv);
            if (r != null) {
                resp.getWriter().write(r);
                break;
            }

        }
    }

    private class HandlerAdapter{
        private Map<String,Integer> paramMapping;

        public HandlerAdapter(Map<String, Integer> paramMapping) {
            this.paramMapping = paramMapping;
        }
        //主要目的是用反射调用url对应的method
        public MyModelAndView handler(HttpServletRequest req,HttpServletResponse resp,Handler handler) throws Exception{
            //为什么要传req，为什么要传resp，为什么传handler
            Class<?>[] parameterTypes = handler.method.getParameterTypes();
            //要想给参数赋值，只能通过索引号来找到具体的某个参数
            Object[] paramValues = new Object[parameterTypes.length];
            Map<String,String[]> params = req.getParameterMap();
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                if (!this.paramMapping.containsKey(entry.getKey())) {
                    continue;
                }
                int index = this.paramMapping.get(entry.getKey());

                //单个赋值是不行的
                paramValues[index] = castStringValue(value, parameterTypes[index]);
            }
            //request 和 response 要赋值
            String reqName = HttpServletRequest.class.getName();
            if (this.paramMapping.containsKey(reqName)) {
                int reqIndex = this.paramMapping.get(reqName);
                paramValues[reqIndex] = req;
            }

            String repsName = HttpServletResponse.class.getName();
            if (this.paramMapping.containsKey(repsName)) {
                int repsIndex = this.paramMapping.get(repsName);
                paramValues[repsIndex] = resp;
            }

            boolean isModelAndView = handler.method.getReturnType() == MyModelAndView.class;
            Object r = handler.method.invoke(handler.controller, paramValues);
            if (isModelAndView) {
                return (MyModelAndView) r;
            }else {
                return null;
            }
        }

        private Object castStringValue(String value,Class<?> clazz){
            if (clazz == String.class) {
                return value;
            }else if (clazz == Integer.class){
                return Integer.valueOf(value);
            }else if(clazz == int.class){
                return Integer.valueOf(value).intValue();
            }else {
                return null;
            }

        }
    }

    /**
     * HandlerMapping定义
     */
    private class Handler{
        protected Object controller;
        protected Method method;
        protected Pattern pattern;

        protected Handler(Object controller, Method method, Pattern pattern) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;
        }
    }

    private class ViewResolver{
        private String viewName;
        private File file;

        protected ViewResolver(String viewName, File file) {
            this.viewName = viewName;
            this.file = file;
        }

        protected String parse(MyModelAndView mv) throws Exception{
            StringBuffer sb = new StringBuffer();
            RandomAccessFile ra = new RandomAccessFile(this.file, "r");
            try {
                //模板框架的语法是非常复杂的，但是，原理都是一样的
                //无非都是用正则表达式来处理字符串而已
                //实现自己的模板语法
                String line = null;
                while (null !=(line = ra.readLine())){
                    Matcher m = matcher(line);
                    while (m.find()) {
                        for (int i = 0; i < m.groupCount(); i++) {
                            String paramName = m.group(i);
                            Object paramValue = mv.getModel().get(paramName);
                            if (null == paramValue) {
                                continue;
                            }
                            line = line.replaceAll("@\\{" + paramName + "\\}",paramValue.toString());
                        }
                    }
                    sb.append(line);
                }
            }finally {
                ra.close();
            }
            return sb.toString();
        }

        private Matcher matcher(String str){
            Pattern pattern = Pattern.compile("@\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(str);
            return m;
        }

        public String getViewName(){
            return viewName;
        }
    }
}
