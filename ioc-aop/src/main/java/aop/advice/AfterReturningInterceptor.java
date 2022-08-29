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

    List<Advice> afterReturning;

    public AfterReturningInterceptor(){
        afterReturning = new ArrayList<>();
    }
    public void add(Advice advice){
        if(!afterReturning.contains(advice))
         afterReturning.add(advice);
    }

    public List<Advice> getAfterReturning() {
        return afterReturning;
    }
}
