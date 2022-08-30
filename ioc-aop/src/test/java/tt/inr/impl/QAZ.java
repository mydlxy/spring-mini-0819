package tt.inr.impl;

import parse.annotation.Autowired;
import parse.annotation.Component;
import parse.annotation.Value;
import tt.A;
import tt.inr.AutowiredTest;
import tt.inr.QWE;

/**
 * @author myd
 * @date 2022/8/20  23:20
 *
 * tt.inr.impl.QAZ
 */

@Component
public class QAZ implements QWE {


    AutowiredTest autowiredTest;

    @Value(" ${t1} ")
    String tt;
    @Value("${ fg }")
    String gh;
    @Value("${test2.name}")
    String nb;
    @Value("my addr")
    String addr;

    @Override
    public String toString() {
        return "QAZ{" +
                "autowiredTest=" +autowiredTest.toString() +
                ", tt='" + tt + '\'' +
                ", gh='" + gh + '\'' +
                ", nb='" + nb + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }

    @Override
    public void print() {
        System.out.println(toString());
    }
}
