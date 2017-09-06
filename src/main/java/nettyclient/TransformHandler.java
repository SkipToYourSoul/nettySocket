package nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by betty.bao on 2017/8/6.
 */
public class TransformHandler extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(TransformHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        int beginReader = in.readerIndex();
        byte dataType = in.readByte();
        if (dataType == 0) {
            in.readerIndex(beginReader);
            super.write(ctx, msg, promise);
            return;
        }
        if(dataType == -1) {
            byte[] fileBytes = readFile("E:/tmp/sensortmp/in/Chrysanthemum.jpg");
            Message customMsg = new Message((byte) 2, fileBytes.length, fileBytes);
            super.write(ctx, customMsg, promise);
//            ctx.channel().writeAndFlush(customMsg);
        }
    }

    //文件内容转为字节数组
    private byte[] readFile(String filePath) throws IOException {
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(filePath);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) ;
            return byteBuffer.array();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error("", e);
            }
            try {
                fs.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }
}