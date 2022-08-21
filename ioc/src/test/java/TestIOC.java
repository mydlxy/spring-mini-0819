import Context.XmlApplicationContext;
import org.junit.Test;
import tt.A;
import tt.B;
import tt.inr.QWE;

/**
 * @author myd
 * @date 2022/8/20  21:45
 */

public class TestIOC {
    @Test
    public void test() throws Throwable {
        XmlApplicationContext context = new XmlApplicationContext("spring.xml,spring1.xml");
        Object bean = context.getBean(A.class);
        System.out.println(bean);
        B bean1 = (B) context.getBean(B.class);
        System.out.println(bean1.a);
        Object bean2 = context.getBean(QWE.class);
        System.out.println(bean2.toString());


    }
}
