package ioc.parse.xml;
import ioc.exception.LabelException;
import ioc.parse.xml.node.Bean;
import ioc.parse.xml.node.ComponentScan;
import ioc.parse.xml.node.PropertyPlaceholder;
import ioc.parse.xml.node.XmlNode;

import java.util.HashMap;
import java.util.Map;
/**
 * @author myd
 * @date 2022/8/9  18:27
 *
 * 注册解析标签的类；
 *
 * <bean>  --->  Bean
 *
 * <propertyPlaceholder>  ----> PropertyPlaceholder
 *
 * <componentScan>  ---->  ComponentScan
 */
public class RegisterParseLabel {
    private  Map<String, XmlNode> xmlNodeMap = new HashMap<>();
    private static RegisterParseLabel registerParseLabel = new RegisterParseLabel();
    public static RegisterParseLabel getRegisterParseLabel(){
        return registerParseLabel;
    }
    {
        registerLabel(Bean.getLabelName(), Bean.getInstance());
        registerLabel(ComponentScan.getLabelName(), ComponentScan.getComponentScan());
        registerLabel(PropertyPlaceholder.getLabelName(), PropertyPlaceholder.getInstance());
    }
    public  void registerLabel(String name, XmlNode node){
        if(xmlNodeMap.containsKey(name))
            throw new LabelException("Label repeat register.");
        xmlNodeMap.put(name,node);
    }

    public void setRegisterParseLabels(Map<String, XmlNode> xmlNodes){
        xmlNodes.forEach((name,node)->registerLabel(name,node));
    }

    public XmlNode getNode(String labelName){
        if(!xmlNodeMap.containsKey(labelName))
            throw new NullPointerException("can not parse label:"+labelName);
        else
            return xmlNodeMap.get(labelName);
    }

}
