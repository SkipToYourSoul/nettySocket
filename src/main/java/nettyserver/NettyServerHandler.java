package nettyserver;

import dataparser.ParseData;
import exception.ParseDataException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pojo.Message;
import utils.DataType;


/**
 * Created by betty.bao on 2017/7/27.
 */
@Component
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    ParseData dataParser = new ParseData();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
        try {
            if (msg instanceof Message) {
                if(msg.getDataType() != 0) {
                    String dataType = DataType.getType(msg.getDataType() & 0xff);
                    doAction(dataType, msg.getBody());
                }
            }
        } catch (EnumConstantNotPresentException e) {
            logger.error("DataType is unsupported !");
        } catch (ParseDataException e) {
            logger.error("error occurs when parsing data");
        } catch (Exception e) {
            logger.error("error occurs when receiving data", e);
        }
    }

    /**
     * 业务分析处理
     * @param dataType
     */
    private void doAction(String dataType, byte[] dataBytes) throws ParseDataException {

        dataParser.persistDataLocally(dataType, dataBytes);
    }

}
