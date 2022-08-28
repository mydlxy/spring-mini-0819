package parse.xml;

import Context.ConfigInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import parse.xml.node.XmlNode;
import parse.xml.node.XmlNodeFactory;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author myd
 * @date 2022/8/19  12:49
 */

public class XmlParse {

    static Logger log = Logger.getLogger(XmlParse.class.getName());

    public static void parseXml(String path, ConfigInfo configInfo) throws DocumentException,NullPointerException {
        String realPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        log.info("[start] parse xml,path="+path);
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(realPath));
        Element root = document.getRootElement();
        List<Element> beans = root.elements();
        for (Element node : beans) {
            String label = node.getName();
            XmlNode xmlNode = XmlNodeFactory.getXmlNode(label);
            xmlNode.parseLabel(node,configInfo);
        }
        log.info("[end] parse xml ending..");
    }
}
