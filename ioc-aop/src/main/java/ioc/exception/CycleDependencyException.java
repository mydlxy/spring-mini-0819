package ioc.exception;

/**
 * @author myd
 * @date 2022/8/19  12:48
 */

public class CycleDependencyException extends  RuntimeException{
    public CycleDependencyException(){}
    public CycleDependencyException(String message){
        super(message);
    }
    public CycleDependencyException(String message, Throwable cause){
        super(message,cause);
    }
    public CycleDependencyException(Throwable cause){
        super(cause);
    }

}
