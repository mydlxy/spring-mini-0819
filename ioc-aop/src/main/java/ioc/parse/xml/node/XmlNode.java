package ioc.parse.xml.node;

import ioc.Context.ConfigInfo;
import org.dom4j.Element;

/**
 * @author myd
 * @date 2022/8/9  18:16
 */

public interface XmlNode {

      void parseLabel(Element node, ConfigInfo configInfo);


}
