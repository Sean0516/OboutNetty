package com.duplicall.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @Description OioClient
 * @Author Sean
 * @Date 2021/4/21 17:37
 * @Version 1.0
 */
public class OioClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup()).channel(OioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                        System.out.println("datagramPacket.toString() = " + datagramPacket.toString());
                    }
                });
        ChannelFuture bind = bootstrap.bind(new InetSocketAddress(40000));
        bind.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                System.out.println("\"success\" = " + "success");
            } else {
                System.out.println("\"bind failed \" = " + "bind failed ");
                channelFuture.cause().printStackTrace();
            }
        });
    }
}
