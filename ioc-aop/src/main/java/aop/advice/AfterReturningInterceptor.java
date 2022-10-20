package aop.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/23  11:28
 */

public class AfterReturningInterceptor {

    Set<Advice> afterReturning;

    public AfterReturningInterceptor(){
        afterReturning = new HashSet<>();
    }
    public void add(Advice advice){
         afterReturning.add(advice);
    }

    public Set<Advice> getAfterReturning() {
        return afterReturning;
    }
}
