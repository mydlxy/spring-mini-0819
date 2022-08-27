package aop.config;

import aop.advice.Advice;

import java.util.*;

/**
 * @author myd
 * @date 2022/8/23  12:48
 */

public class AopConfig {

    /**
     * <aop:config>
     *     <aop:pointcut  id="" expression="" />
     *
     *     <aop:aspect  ref="">
     *         <aop:before pointcut=""  method=""/>
     *         <aop:after pointcut-ref=""  method=""/>
     *         <aop:after-returning pointcut=""  method=""/>
     *         <aop:after-throwing pointcut=""  method="" exception=""/>
     *         <aop:around pointcut=""  method=""/>
     *     </aspect>
     * </aop:config>
     *
     */
    /**
     * <aop:pointcut   id="" expression="" />
     */
    Map<String,String> pointcuts;


    /**
     *     String refBean ;//aspect bean 切面类
     *
     *     Set<Advice> advice =  new HashSet<>();
     *
     */
    Map<String , Set<Advice>> aspect = new HashMap<>();






}
