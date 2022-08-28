package aop.advice;

import java.util.HashSet;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/23  11:28
 */

public class AfterThrowingInterceptor {
    Set<Advice> afterThrowing;

    public AfterThrowingInterceptor(){
        afterThrowing = new HashSet<>();
    }
    public void add(Advice advice){
        afterThrowing.add(advice);
    }

    public Set<Advice> getAfterThrowing() {
        return afterThrowing;
    }
}
