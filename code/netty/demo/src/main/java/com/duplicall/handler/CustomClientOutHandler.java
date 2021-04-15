package com.duplicall.handler;

import io.netty.channel.*;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * @Description OutHandler
 * @Author Sean
 * @Date 2021/4/12 14:48
 * @Version 1.0
 */
public class CustomClientOutHandler extends ChannelOutboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        logger.info("remote addr [{}]  connect success promise [{}]", remoteAddress.toString(), promise.isSuccess());
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        logger.info("local addr bind [{}] ", localAddress.toString());
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("dis connect [{}]", ctx.channel().remoteAddress().toString());
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel close ");
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel de register ");
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("msg [{}]", msg);
        promise.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                logger.info("write  msg to server success ");
            }else {
                logger.error("write msg to server error as [{}]",channelFuture.cause().getMessage(),channelFuture.cause());

            }
        });
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
