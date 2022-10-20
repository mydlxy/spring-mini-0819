package tt.inr;

import ioc.parse.annotation.Component;
import ioc.parse.annotation.Value;

/**
 * @author myd
 * @date 2022/8/30  12:43
 */

@Component
public class AutowiredTest {
    @Value("${autowired.q1 }")
    String q1;
    @Value("dgd")
    String q2;
    @Value("1")
    int yy;
    @Value("true")
    boolean rr;
    @Value("3.13")
    Float aFloat;

    public void print(){
        System.out.println("AutowiredTest{" +
                "q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", yy=" + yy +
                ", rr=" + rr +
                ", aFloat=" + aFloat +
                '}');
    }


    @Override
    public String toString() {
        return "AutowiredTest{" +
                "q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", yy=" + yy +
                ", rr=" + rr +
                ", aFloat=" + aFloat +
                '}';
    }
}
