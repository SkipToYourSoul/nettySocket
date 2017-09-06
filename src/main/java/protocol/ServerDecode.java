package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;
import pojo.Message;

import java.util.List;

/**
 * 报文解析协议--服务器端
 * Created by betty.bao on 2017/7/30.
 */
@Component
public class ServerDecode extends ByteToMessageDecoder {

    //判断是否是有效报文。头部信息的大小应该是 byte+int = 1+4 = 5
    private static final int HEADER_SIZE = 5;

    private byte dataType; //指定传输的数据类型

    private int length;

    private byte[] body;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() >= HEADER_SIZE) {

            // 记录包头开始的index
            int beginReader = in.readerIndex();
            dataType = in.readByte();
            // 消息的长度
            length = in.readInt();
            // 判断请求数据包数据是否到齐
            if (in.readableBytes() < length) {
                // 还原读指针
                in.readerIndex(beginReader);
                return;
            }

            // 读取body数据
            body = new byte[length];
            in.readBytes(body);
            Message msg = new Message(dataType, length, body);
            out.add(msg);
        }
    }

}