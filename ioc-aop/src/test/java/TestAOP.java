import Context.ApplicationContext;
import Context.XmlApplicationContext;
import aop.parse.xml.AdvisorParse;
import org.junit.Test;
import parse.xml.XmlParse;
import parse.xml.node.XmlNode;
import tt.Aspected;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myd
 * @date 2022/8/29  0:59
 */

public class TestAOP {
    @Test
    public void test() throws Throwable {


        Map<String , XmlNode> labelParse  = new HashMap<>();

        labelParse.put("aopConfig", AdvisorParse.getInstance());
        ApplicationContext context =
                new XmlApplicationContext("spring.xml,spring1.xml,aop.xml",labelParse,null);
        Aspected aspected = (Aspected) context.getBean(Aspected.class);

//        aspected.aroundTest();

        aspected.beforeTest();

    }
    @Test
    public void test1(){


        String t = "*Test";
        String t2 = t.replaceAll("\\*","\\\\w*");
        System.out.println("\\w*Test");
        System.out.println(t2);
        if("beforeTest".matches(t.replaceAll("\\*","\\\\w*"))){
            System.out.println("match..");
        }
    }
}
