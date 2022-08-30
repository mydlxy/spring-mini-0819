package tt.inr;

import parse.annotation.Component;
import parse.annotation.Value;

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
    @Override
    public String toString() {
        return "AutowiredTest{" +
                "q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", yy=" + yy +
                ", rr=" + rr +
                '}';
    }
}
