package ioc.parse.xml.node;

import ioc.Context.ConfigInfo;
import ioc.bean.BeanDefinition;
import org.dom4j.Element;
import ioc.parse.annotation.Component;
import ioc.utils.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author myd
 * @date 2022/8/10  1:34
 */

public class ComponentScan implements XmlNode {


    private static Logger log = Logger.getLogger(ComponentScan.class.getName());
//    rsfs
    private static final String LABEL_NAME ="componentScan";
    private static final String PACKAGE = "package";
    private static ComponentScan componentScan = new ComponentScan();
    //解析的注解类型集合
    private Set<Class<? extends Annotation> > scanType = new HashSet<>();
    {
        scanType.add(Component.class);
    }

    public static ComponentScan getComponentScan(){
        return componentScan;
    }

    public static String getLabelName(){
        return LABEL_NAME;
    }

    public void addScanType(Class<? extends Annotation> scanClass){
        scanType.add(scanClass);
    }

    public void addScanType(List<Class<? extends Annotation>> scanClasses){
        scanClasses.forEach(scanClass->addScanType(scanClass));
    }

    @Override
    public void parseLabel(Element node, ConfigInfo configInfo) {

        String scanPackage = node.attributeValue(PACKAGE);
        String path =Thread.currentThread().getContextClassLoader().getResource("").getPath();

        if(scanPackage != null)
            path += scanPackage.replaceAll("\\.","/");

        log.info("scan path:"+path);

        //获取到给定路径下的所有类名
        List<String> classNames = new ArrayList<>();
        AnnotationUtils.getAllClassNames(path,scanPackage,classNames);
        //筛选出有@Com
        List<BeanDefinition> beanDefinitions = null;
        try {
            beanDefinitions = AnnotationUtils.scanAndGetBeanDefinitions(classNames,scanType);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        beanDefinitions.forEach(beanDefinition -> configInfo.registerBean(beanDefinition.getBeanName(),beanDefinition));

    }








}
