package nettyclient;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import nettyserver.ChannelHandlerHolder;
import protocol.ClientEncode;

/**
 * Created by betty.bao on 2017/8/2.
 */
public class CommonChannelInitializer implements ChannelHandlerHolder {

    @Override
    public ChannelHandler[] handlers() {
        return new ChannelHandler[]{
                // out
                new ClientEncode(),
                new TransformHandler(), // transfer data
                // in
                new StringDecoder(),
                new ClientActiveHandler(),// register client
                new ClientEncode()
        };
    }
}
