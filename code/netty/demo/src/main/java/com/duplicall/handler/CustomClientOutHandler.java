package com.duplicall.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
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
        logger.info("remote addr [{}] local addr [{}] connect success promise [{}]", remoteAddress.toString(), localAddress.toString(), promise.isSuccess());
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        logger.info("local addr bind [{}] ", localAddress.toString());
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("dis connect [{}]", ctx.channel().remoteAddress().toString());
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel close ");
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel de register ");
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("msg [{}]", msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
    }
}
