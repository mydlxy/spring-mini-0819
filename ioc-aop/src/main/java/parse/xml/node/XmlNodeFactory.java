package parse.xml.node;

import parse.xml.RegisterParseLabel;

/**
 * @author myd
 * @date 2022/8/9  18:16
 */

public class XmlNodeFactory {
    private static RegisterParseLabel registerParseLabel = RegisterParseLabel.getRegisterParseLabel();
    public static XmlNode getXmlNode(String name){
       return registerParseLabel.getNode(name);
    }

}
