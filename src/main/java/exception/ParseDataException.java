package exception;

import java.io.IOException;

/**
 * Created by betty.bao on 2017/7/28.
 */
public class ParseDataException extends IOException {

    private static final long serialVersionUID = 929070906392432510L;

    public ParseDataException(String msg){
        super(msg);
    }
}
