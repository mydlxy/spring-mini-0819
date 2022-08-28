package tt;

/**
 * @author myd
 * @date 2022/8/29  0:56
 */

public class Aspected {//被切面增强的类

   public void beforeTest(){

        System.out.println("......before  method.....");
    }
    public void afterTest(){

        System.out.println("......after  method.....");
    }

    public void afterReturningTest(){

        System.out.println("......afterReturning  method.....");
    }

    public void afterThrowingTest(){

        System.out.println("......afterThrowingTest  method.....");
    }

    public void aroundTest(){

        System.out.println("......around  method.....");
    }



}
