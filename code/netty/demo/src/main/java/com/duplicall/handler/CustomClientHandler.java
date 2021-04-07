package com.duplicall.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description CustomClientHandler
 * @Author Sean
 * @Date 2021/4/7 10:29
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class CustomClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /**
     * 当被通知 channel是活跃的时候发送一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Netty Server", CharsetUtil.UTF_8));
    }

    /**
     * 打印服务端发送的消息
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        logger.info("client received   msg [{}]",byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 发生异常时,记录错误并关闭 (可以考虑重连)
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("server error as [{}]",cause.getMessage(),cause);
        ctx.close();
    }
}
