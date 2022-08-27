package exception;

/**
 * @author myd
 * @date 2022/8/9  18:35
 */

public class LabelException extends  RuntimeException {

    public LabelException(){}
    public LabelException(String message){
        super(message);
    }
    public LabelException(String message, Throwable cause){
        super(message,cause);
    }
    public LabelException(Throwable cause){
        super(cause);
    }

}
