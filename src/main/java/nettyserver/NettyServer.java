package nettyserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket服务器端
 * Created by betty.bao on 2017/7/27.
 */
public class NettyServer implements  Runnable{

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    //    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private int port;
    private ChannelHandlerHolder channelHandlerHolder;

    public  NettyServer(int port, ChannelHandlerHolder channelHandlerHolder){
        this.port = port;
        this.channelHandlerHolder = channelHandlerHolder;
    }

    @Override
    public void run(){
        try {
            start();
        }
        catch (InterruptedException ie){

        }
    }
    /**
     * socket服务器配置
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        try {

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // UDP NioDatagramChannel.class
                    .option(ChannelOption.SO_BACKLOG, 1024) //连接数
                    .option(ChannelOption.TCP_NODELAY, true)  //不延迟，消息立即发送
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<Channel>() {

                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(channelHandlerHolder.handlers());
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            logger.info("============== init netty server success ===============");
            logger.info("start server at port: " + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
