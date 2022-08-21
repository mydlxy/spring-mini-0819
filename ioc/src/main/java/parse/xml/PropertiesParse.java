package parse.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author myd
 * @date 2022/8/4  9:41
 */

public class PropertiesParse {

    private static Logger log =  Logger.getLogger(PropertiesParse.class.getName());

    public static Properties parseProperties(String path) throws IOException {

        String loadPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        Properties parse = new Properties();
        log.info("[start] parse properties:"+path);
        FileInputStream ios = null;
        try {
            ios = new FileInputStream(loadPath);
            parse.load(ios);
        } finally{
            if(ios != null){
                ios.close();
            }
        }
        log.info("[end] parse properties :"+path+" .......... ");
        return parse;
    }


}
