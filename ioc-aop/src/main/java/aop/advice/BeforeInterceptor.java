package aop.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/23  11:27
 */

public class BeforeInterceptor {

    List<Advice> before;

    public BeforeInterceptor(){
        before = new ArrayList<>();
    }

    public List<Advice> getBefore() {
        return before;
    }

    public void add(Advice advice){
        if(!before.contains(advice))
             before.add(advice);
    }

}
