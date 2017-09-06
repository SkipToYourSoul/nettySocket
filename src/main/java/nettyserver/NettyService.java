package nettyserver;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettyService {

    private static final Logger logger = LoggerFactory.getLogger(NettyService.class);
    public static  Map<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();

    ChannelHandlerHolder channelHandlerHolder = new ServerChannelInitializer();


    public void run()  {
        try {
            NettyServer nettyServer = new NettyServer(5879, channelHandlerHolder);
            Thread nettyThread = new Thread(nettyServer, "Netty-Server");
            nettyThread.start();
        } catch (Exception e) {
            logger.error("Server Start Failure. ->" + e.getMessage(), e);
        }
    }

    public String openChannelToTransform(final String doCode) {
        if (!channelMap.containsKey(doCode)) {
            return "client has not register to the server";
        }
        Runnable sendTask = new Runnable() {
            @Override
            public void run() {
                Channel obj;
                try {
                    obj = channelMap.get(doCode);
                    System.out.println("active channel id is: " + doCode);
                    obj.writeAndFlush("active client code is: " + doCode);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        };
        new Thread(sendTask).start();
        return "success";
    }

    public static void addChannel(String decoder, Channel channel){
        channelMap.put(decoder, channel);
    }

    public static void main(String[] args) {
        NettyService service = new NettyService();
        service.run();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            service.openChannelToTransform(br.readLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
