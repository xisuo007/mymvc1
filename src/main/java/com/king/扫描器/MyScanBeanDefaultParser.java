package com.king.扫描器;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


/**
 * Created by ljq on 2020/7/22 15:53
 * 自定义扫描器    找到指定路径下的自定义注解
 */
public class MyScanBeanDefaultParser implements BeanDefinitionParser {
    private static final String RESOURCE_PATTERN = "/**/*.class";
    private ResourcePatternResolver resourcePattern = new PathMatchingResourcePatternResolver();
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String myPackage = element.getAttribute("cn");
        if (StringUtils.hasText(myPackage)) {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(myPackage)
                    + RESOURCE_PATTERN;
            try {
                Resource[] resources = resourcePattern.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePattern);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        Class<?> aClass = Class.forName(className);
                        MyAnn annotation = aClass.getAnnotation(MyAnn.class);
                        if (annotation != null) {
                            System.out.println("自定义注解");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
