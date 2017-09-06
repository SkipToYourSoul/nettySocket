package idle;

/**
 * Created by betty.bao on 2017/8/1.
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import nettyserver.ChannelHandlerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 当发现当前的链路不稳定关闭之后，进行12次重连
 */
@ChannelHandler.Sharable
public abstract class ConnectionWatcher extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionWatcher.class);

    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;
    private final String host;

    private volatile boolean reconnect = true;
    private int attempts;


    public ConnectionWatcher(Bootstrap bootstrap, Timer timer, int port, String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    /**
     * channel链路每次激活的时候，将其连接的次数重新置为0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        attempts = 0;
        System.out.println("当前链路已经激活了，重连尝试次数重新置为0");
        logger.info("-----channel is active, the attempts is 0-----");
        ctx.fireChannelActive();
    }

    /**
     * channel链路不稳定时，尝试重连
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接关闭");
        logger.info("-----channel is closed-----");
        if (reconnect) {
            System.out.println("链接关闭，将进行重连");
            logger.info("-----trying to reconnect channel-----");
            if (attempts < 12) {
                attempts++;
                //重连的间隔时间会越来越长
                int timeout = 2 << attempts;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }

    /**
     * 定时任务，重连服务器
     * @param timeout
     * @throws Exception
     */
    @Override
    public void run(Timeout timeout) throws Exception {

        ChannelFuture future;
        //bootstrap已经初始化好了，只需要将handler填入就可以了
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {

                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }
        //future对象
        future.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();

                //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                if (!succeed) {
                    System.out.println("重连失败");
                    logger.info("----reconnect failure, trying again----");
                    f.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("重连成功");
                    logger.info("------reconnect succeed------");
                }
            }
        });
    }
}
