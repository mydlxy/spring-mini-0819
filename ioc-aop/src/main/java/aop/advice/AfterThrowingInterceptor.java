package aop.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/23  11:28
 */

public class AfterThrowingInterceptor {
    List<Advice> afterThrowing;

    public AfterThrowingInterceptor(){
        afterThrowing = new ArrayList<>();
    }
    public void add(Advice advice){
        if(!afterThrowing.contains(advice))
            afterThrowing.add(advice);
    }

    public List<Advice> getAfterThrowing() {
        return afterThrowing;
    }
}
