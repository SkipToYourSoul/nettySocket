package nettyclient;

import idle.ConnectionWatcher;
import idle.ConnectorIdleStateTrigger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import nettyserver.ChannelHandlerHolder;
import protocol.ClientEncode;

import java.util.concurrent.TimeUnit;

/**
 * Created by betty.bao on 2017/8/2.
 */
public class WatcherChannelInitializer implements ChannelHandlerHolder {

    private  Bootstrap boot;
    private  HashedWheelTimer timer = new HashedWheelTimer();
    private  int port;
    private  String host;
    private volatile boolean reconnect = true;
    private ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

    public WatcherChannelInitializer(Bootstrap boot, int port, String host, boolean reconnect) {
        this.boot = boot;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }


    final ConnectionWatcher watcher = new ConnectionWatcher(boot, timer, port, host, true) {

        public ChannelHandler[] handlers() {
            return new ChannelHandler[]{
                    // out
                    new TransformHandler(), // transfer data
                    // in
                    new ClientActiveHandler(),
                    new ClientEncode(),
                    this,
                    new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                    idleStateTrigger
            };
        }
    };

    @Override
    public ChannelHandler[] handlers() {
        return watcher.handlers();
    }
}
