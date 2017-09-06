package exception;

import java.util.concurrent.ThreadFactory;

/**
 * Created by betty.bao on 2017/7/26.
 */
public class HandlerThreadFactory  implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new ClientUnCaughtException());//设定线程工厂的异常处理器
        return t;
    }
}