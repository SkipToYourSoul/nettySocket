package nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import nettyserver.ChannelHandlerHolder;
import org.apache.log4j.Logger;


/**
 * socket客户端
 * Created by betty.bao on 2017/7/27.
 */
public class NettyClient {

    private static final Logger logger = Logger.getLogger(NettyClient.class);

    private ChannelHandlerHolder channelHandlerHolder;

    /**
     * 客户端socket配置
     *
     * @param port
     * @param host
     * @throws Exception
     */
    public void connect(int port, String host) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).handler(new LoggingHandler(LogLevel.INFO));

//        channelHandlerHolder = new WatcherChannelInitializer(boot, port, host, true);
            channelHandlerHolder = new CommonChannelInitializer();

        boot.handler(new ChannelInitializer<Channel>() {

            //初始化channel
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(channelHandlerHolder.handlers());
            }
        });
        boot.connect(host, port);
        logger.info("----------------connect server success----------------");
    }

    public static void main(String[] args) throws Exception {
        int port = 5879;
        new NettyClient().connect(port, "127.0.0.1");
    }
}
