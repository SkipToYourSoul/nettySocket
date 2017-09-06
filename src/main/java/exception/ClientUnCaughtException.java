package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by betty.bao on 2017/7/26.
 */
public class ClientUnCaughtException implements Thread.UncaughtExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientUnCaughtException.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("ClientException :" , e);
    }
}