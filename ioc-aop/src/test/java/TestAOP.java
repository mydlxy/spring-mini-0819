import ioc.Context.ApplicationContext;
import ioc.Context.XmlApplicationContext;
import aop.parse.xml.AdvisorParse;
import org.junit.Test;
import ioc.parse.xml.node.XmlNode;
import tt.Aspected;
import tt.inr.QWE;
import tt.inr.impl.QAZ;

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
                new XmlApplicationContext("spring.xml,spring1.xml,aop.xml",labelParse,null);//
        Aspected aspected = (Aspected) context.getBean(Aspected.class);

        QWE qaz = (QWE) context.getBean(QWE.class);
        qaz.print();


    }
    @Test
    public void test1(){

        QAZ t = new QAZ();
        Class<?>[] interfaces = t.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface.getTypeName());
        }



    }
}
