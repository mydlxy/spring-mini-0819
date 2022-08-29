package tt.inr.impl;

import parse.annotation.Component;
import parse.annotation.Value;
import tt.inr.QWE;

/**
 * @author myd
 * @date 2022/8/20  23:20
 *
 * tt.inr.impl.QAZ
 */

@Component
public class QAZ implements QWE {

    @Value(" ${t1} ")
    String tt;
    @Value("${ fg }")
    String gh;
    @Value("${name}")
    String nb;

    @Override
    public String toString() {
        return "QAZ{" +
                "tt='" + tt + '\'' +
                ", gh='" + gh + '\'' +
                ", nb='" + nb + '\'' +
                '}';
    }

    @Override
    public void print() {
        System.out.println(toString());
    }
}
