package aop.parse.utils;

import aop.advice.Advice;
import aop.config.PointcutUtils;
import aop.parse.xml.AdvisorParse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/30  10:32
 */

public class AOPUtils {

    public static  void isNull(String str,String message){
        if(str == null || str.trim().length() == 0 )throw new NullPointerException("null error " +message);
    }

    public static void exeAdvice(Set<Advice> advices, Method method) throws InvocationTargetException, IllegalAccessException {
        if(!advices.isEmpty()) {
            for (Advice advice : advices)
                if(PointcutUtils.matchMethod(advice.getPointcut(),method))
                    advice.advice();
        }
    }


    public static void checkAspectLabel(String type){

        switch(type){
            case AdvisorParse.AFTER:
            case AdvisorParse.AROUND:
            case AdvisorParse.AFTER_RETURNING_ELEMENT:
            case AdvisorParse.BEFORE:
            case AdvisorParse.AFTER_THROWING_ELEMENT:
                break;
            default:
                throw new RuntimeException("aspect label error,can not parse label:"+ type);
        }
    }
}
