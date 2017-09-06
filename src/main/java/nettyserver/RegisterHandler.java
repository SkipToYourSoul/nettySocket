package nettyserver;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Message;

/**
 * Created by baoting1104 on 17/8/5.
 */
@ChannelHandler.Sharable
public class RegisterHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger logger = LoggerFactory.getLogger(RegisterHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        if (msg instanceof Message) {
            if (msg.getDataType() == 0) {
                NettyService.addChannel(new String(msg.getBody()), ctx.channel());
                System.out.println("current client id is: " + new String(msg.getBody()));
            }
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
