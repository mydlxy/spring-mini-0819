package aop.parse.xml;

import ioc.Context.ConfigInfo;
import aop.advice.Advice;
import aop.config.PointcutUtils;
import aop.parse.utils.AOPUtils;
import org.dom4j.Element;
import ioc.parse.xml.node.XmlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2022/8/21  15:43
 */

public class AdvisorParse implements XmlNode {

    public static final String ASPECT = "aspect";
    public static final String ID = "id";

    private static final String METHOD_NAME = "method";
    private static final String POINTCUT = "pointcut";
    private static final String ADVICE_BEAN_NAME = "adviceBeanName";
    private static final String ADVISOR = "advisor";
    private static final String ADVICE_REF = "advice-ref";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String AFTER_RETURNING_ELEMENT = "afterReturning";
    public static final String AFTER_THROWING_ELEMENT = "afterThrowing";
    public static final String AROUND = "around";
    private static final String RETURNING = "returning";
    private static final String RETURNING_PROPERTY = "returningName";
    private static final String THROWING = "throwing";
    private static final String THROWING_PROPERTY = "throwingName";
    private static final String ARG_NAMES = "arg-names";
    private static final String ARG_NAMES_PROPERTY = "argumentNames";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";
    private static final String DECLARATION_ORDER_PROPERTY = "declarationOrder";
    private static final String ORDER_PROPERTY = "order";
    private static final int METHOD_INDEX = 0;
    private static final int POINTCUT_INDEX = 1;
    private static final int ASPECT_INSTANCE_FACTORY_INDEX = 2;

    /**
     *
     * <aopConfig>
     *    * XXXXXXXXX<aop:pointcut id="pointcut-1"  expression="xxxxxx"  />XXXXXXXXX
     *      <aop:aspect id="" ref="">
     *          <aop:before pointcut=""  method=""/> 执行targetMethod方法前执行
     *          <aop:after pointcut="pointcut-1"   method=""/>  发生异常也执行finally
     *          <aop:after-returning pointcut="" method="" />执行targetMethod之后执行
     *          <aop:after-throwing pointcut=""  method="" exception="" />发生异常之后执行；
     *          <aop:around pointcut ="" method="" /> 是前几个的集合。。
     *      </aop:aspect>
     * </aopConfig>
     */

    private static final AdvisorParse advisorParse = new AdvisorParse();

    public static AdvisorParse getInstance(){
        return advisorParse;
    }

    @Override
    public void parseLabel(Element node, ConfigInfo configInfo) {
        List<Element> aspects = node.elements();
        aspects.forEach(aspect->{
            if(!aspect.getName().equals(ASPECT))
                throw new RuntimeException("aopConfig子标签配置错误；不能解析标签："+aspect.getName());
            aspectParse(aspect,configInfo);
        });
    }


    public void aspectParse(Element aspect,ConfigInfo configInfo){
       String ref = aspect.attributeValue(REF);
       AOPUtils.isNull(ref," , aspect's ref bean is null");
       String id = aspect.attributeValue(ID);
       if(id == null || id.trim().length() == 0)id =ref;
       String finalUseId = id+":"+ref;
        List<Advice> advices = new ArrayList<>();
        List<Element> adviceList = aspect.elements();
        for (Element element : adviceList) {
            Advice advice =  adviceParse(element,ref,finalUseId);
            advices.add(advice);
        }
        configInfo.addAspect(finalUseId,advices);
    }

    public Advice adviceParse(Element advice,String ref,String id){
        String type  = advice.getName();
        AOPUtils.checkAspectLabel(type);
        String pointcut = advice.attributeValue(POINTCUT);
        String methodName  = advice.attributeValue(METHOD_NAME);
        AOPUtils.isNull(pointcut,",aspect id = "+id +",type = "+type+", pointcut is null");
        AOPUtils.isNull(methodName,",aspect id = "+id +",type = "+type+", method is null");
        return new Advice(ref,type, PointcutUtils.parsePointcut(pointcut),methodName);
    }

}
