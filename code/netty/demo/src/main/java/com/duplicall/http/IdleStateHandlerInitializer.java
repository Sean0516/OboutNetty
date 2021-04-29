package com.duplicall.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Description IdleStateHandlerInitializer
 * @Author Sean
 * @Date 2021/4/28 17:45
 * @Version 1.0
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
//       如果连接超过60 秒没有接收或者发送任何数据， IdleStateHandler使用IdleStateEvent 事件 ，来调用fireUserEventTriggered ，
//        实现了userEventTriggered 的handler 将会发送对应的心跳
        pipeline.addLast(new IdleStateHandler(0, 0, 60));
        pipeline.addLast(new HeartBeatHandler());
        pipeline.addLast(new ReadTimeoutHandler(2, TimeUnit.MINUTES));
        pipeline.addLast(new CustomReadTimeOutHandler());

    }

    public final class HeartBeatHandler extends ChannelInboundHandlerAdapter {
        private final ByteBuf heartBeat = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("heartbeat", CharsetUtil.UTF_8));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//            当接收到 IdleStateEvent ，发送心跳信息 ，并且添加一个发送失败则关闭连接的 listener
            if (evt instanceof IdleStateEvent) {
                ctx.writeAndFlush(heartBeat.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
//                如果不是，则传递下一个 handler
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    public final class CustomReadTimeOutHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if (cause instanceof ReadTimeoutException) {
                logger.error("happen read time out exception  ");
            } else {
                super.exceptionCaught(ctx, cause);
            }

        }
    }
}
