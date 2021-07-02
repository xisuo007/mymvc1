package com.king.framework.context;

import com.king.framework.annotation.MYAutowired;
import com.king.framework.annotation.MYController;
import com.king.framework.annotation.MYService;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ljq on 2018/11/29 14:25
 * 自定义IOC容器
 */
public class MyApplicationContext {
    private Map<String,Object> instanceMapping = new ConcurrentHashMap<>();
    //类似于内部的配置信息，外面看不到
    //能看到的只有ioc容器 getBean方法来间接调用的
    private List<String> classCache = new ArrayList<>();
    private Properties config = new Properties();


    public MyApplicationContext(String location) {
        InputStream in = null;
        try {
         //1.定位
            in = this.getClass().getClassLoader().getResourceAsStream(location);
         //2.载入
            config.load(in);
         //3.注册，把所有的class找出来存起来
            String packageName = config.getProperty("scanPackage");
            doRegister(packageName);
         //4.实例化需要ioc的对象(就是加了@Service，@Controller)，只要循环calss
            doCreatBean();
         //5.注入
            populate();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("IOC 容器已经初始化");
    }


    //把符合条件的class全部都找出来，注册到缓存里去
    private void doRegister(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if (file.isDirectory()) {
                doRegister(packageName+"."+file.getName());
            }else {
                classCache.add(packageName+"."+file.getName().replace(".class","").trim());
            }
        }
    }

    private void doCreatBean() {
        //检查看有没有注册信息，注册信息里面保存了所有的class名字
        //BeanDefinition 保存了类的名字，也保存类和类之间的关系（Map/list/Set/ref/parent）
        //processArray
        if (classCache.size() == 0) {
            return;
        }
        try {
            for (String className : classCache) {
                //知道这里有一个套路
                Class<?> clazz = Class.forName(className);
                //哪个类需要初始化，哪个类不需要初始化
                //只要加了 @Service  @Controller都要初始化
                if (clazz.isAnnotationPresent(MYController.class)) {
                    //初始化的类名称 ，默认类名首字母小写
                    String id = lowerFirstChar(clazz.getSimpleName());
                    instanceMapping.put(id,clazz.newInstance());
                }else if (clazz.isAnnotationPresent(MYService.class)){
                    MYService service = clazz.getAnnotation(MYService.class);
                    //如果设置了自定义名字，就优先用他自己定义的名字
                    String id = service.value();
                    if (!"".equals(id.trim())) {
                        instanceMapping.put(id,clazz.newInstance());
                        continue;
                    }
                    //如果是空的，就用默认的规则，1.类名首字母小写
                    //如果这个类是接口，2.可以根据类型匹配
                    Class<?>[] interfaces = clazz.getInterfaces();
                    //如果这个类实现了接口，就用接口的类型作为id
                    for (Class<?> i : interfaces) {
                        instanceMapping.put(i.getName(),clazz.newInstance());
                    }
                }else {
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 依赖注入
     */
    private void populate() {
        //首先要判断ioc容器中有没有东西
        if (instanceMapping.size() == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMapping.entrySet()) {
            //把所有的属性全部取出来，包括私有属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MYAutowired.class)) {
                    continue;
                }
                MYAutowired autowired = field.getAnnotation(MYAutowired.class);
                String id = autowired.value().trim();
                //如果id为空，也就是说，自己没有设置，默认根据类型来注入
                if ("".equals(id)) {
                    id = field.getType().getName();
                }
                field.setAccessible(true);//把私有变量开放访问权限
                try {
                    field.set(entry.getValue(),instanceMapping.get(id));
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * 将首字母小写
     * @param str
     * @return
     */
    private String lowerFirstChar(String str) {
        return String.valueOf(str.toCharArray()[0] += 32);
    }

    public Map<String,Object> getAll(){return instanceMapping;}

    public Properties getConfig(){return config;}
}
