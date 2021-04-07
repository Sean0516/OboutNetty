package com.duplicall.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description CustomServerHandler
 * @Author Sean
 * @Date 2021/4/6 16:12
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class CustomServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取接收的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String addr = ctx.channel().localAddress().toString();
        logger.info("received msg [{}]", in.toString(CharsetUtil.UTF_8));
        ctx.write(Unpooled.copiedBuffer("Hello Client " + addr, CharsetUtil.UTF_8));
    }

    /**
     * 将未决消息冲刷到远程节点，并且关闭该 Channel
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("read msg end ");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    /**
     * 打印异常 并关闭channel
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("error [{}]", cause.getMessage(), cause);
        ctx.close();
    }
}
