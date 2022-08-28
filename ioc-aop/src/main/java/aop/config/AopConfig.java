package aop.config;

import aop.advice.Advice;

import java.util.*;

/**
 * @author myd
 * @date 2022/8/23  12:48
 */

public class AopConfig {

    /**
     * <config>
     *     <aspect  ref="">
     *         <before pointcut=""  method=""/>
     *         <after pointcut-ref=""  method=""/>
     *         <after-returning pointcut=""  method=""/>
     *         <after-throwing pointcut=""  method="" exception=""/>
     *         <around pointcut=""  method=""/>
     *     </aspect>
     * </config>
     *
     */
    /**
     * <aop:pointcut   id="" expression="" />
     */


    /**
     *     String refBean ;//aspect bean 切面类
     *
     *     Set<Advice> advice =  new HashSet<>();
     *
     */
    Map<String , Set<Advice>> aspect = new HashMap<>();






}
