package com.duplicall.udp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @Description ClientHandler
 * @Author Sean
 * @Date 2021/4/30 11:27
 * @Version 1.0
 */
public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf data = datagramPacket.content();
        logger.info("receive server  msg [{}]",data.toString(Charset.defaultCharset()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("client deal error as [{}]",cause.getMessage(),cause);
        ctx.close();
    }
}
