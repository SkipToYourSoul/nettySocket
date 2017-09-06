package nettyclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;
import pojo.Message;

/**
 * parse and deal with data tranferred from server
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class ClientActiveHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端连接到服务器时即发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] req = "10001".getBytes();
//        ByteBuf buf = Unpooled.buffer(req.length);
//        buf.writeBytes(req);
//        ctx.writeAndFlush(buf);
        Message customMsg = new Message((byte) 0, req.length, req);
        ctx.channel().writeAndFlush(customMsg);
    }

    /**
     * 客户端接收来自服务器的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

//        byte[] req = "Register Success".getBytes();
//        ByteBuf buf = Unpooled.buffer(req.length);
//        buf.writeBytes(req);
//        ctx.write(buf);
        System.out.println("Server said:" + msg);
        Message customMsg = new Message((byte) -1, "action".getBytes().length, "action".getBytes());
        ctx.channel().writeAndFlush(customMsg);
//        result.release();
    }
}