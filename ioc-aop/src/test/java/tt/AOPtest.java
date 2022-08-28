package tt;

/**
 * @author myd
 * @date 2022/8/29  0:49
 */

public class AOPtest {

    void before(){
        System.out.println("aspect before test");
    }
    void after(){
        System.out.println("aspect after test");
    }
    void afterThrowing(){
        System.out.println("aspect afterThrowing test");
    }
    void afterReturning(){
        System.out.println("aspect afterReturning test");
    }
    void around(){
        System.out.println("aspect around test");
    }


}
