package exception;

/**
 * 报文解析不满足协议时抛出的异常
 * Created by betty.bao on 2017/7/30.
 */
public class ProtocolException extends Exception {


    private static final long serialVersionUID = 895657234092736731L;

    public ProtocolException(String msg){
        super(msg);
    }
}
