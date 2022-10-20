package ioc.parse.xml.node;


import ioc.Context.ConfigInfo;
import ioc.parse.xml.PropertiesParse;
import org.dom4j.Element;

import java.io.IOException;
import java.util.Properties;

/**
 * @author myd
 * @date 2022/8/9  19:12
 */

public class PropertyPlaceholder implements XmlNode {

    private static final String LABEL_NAME ="propertyPlaceholder";

    private static final String LOAD = "load";

    private static final PropertyPlaceholder propertyPlaceholder = new PropertyPlaceholder();

    public static String getLabelName() {
        return LABEL_NAME;
    }

    public static XmlNode getInstance() {
        return propertyPlaceholder;
    }

    @Override
    public void parseLabel(Element node, ConfigInfo configInfo) {
        String path = node.attributeValue(LOAD);
        try {
            Properties properties = PropertiesParse.parseProperties(path);
            configInfo.addProperties(getFileName(path),properties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public String getFileName(String path){
        int start = path.lastIndexOf("/");
        int last = path.lastIndexOf(".");
        return path.substring(start+1,last);
    }


}
