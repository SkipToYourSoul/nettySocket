package nettyserver;

import idle.AcceptorIdleStateTrigger;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import protocol.ServerDecode;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer implements ChannelHandlerHolder {

    AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();
    NettyServerHandler nettyServerHandler = new NettyServerHandler();

    public ChannelHandler[] handlers() {
        return new ChannelHandler[]{
                // out
//                new OutBoundHandler(),
                new StringEncoder(),
//                new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS),
                // in
//                new StringDecoder(),
                new ServerDecode(),   // decode message with protocol
                new RegisterHandler(),  // register client to server
//                idleStateTrigger,
                nettyServerHandler  // parse and restore data
        };
    }
}