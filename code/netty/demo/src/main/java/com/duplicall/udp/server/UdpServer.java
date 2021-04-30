package com.duplicall.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @Description UdpServer
 * @Author Sean
 * @Date 2021/4/30 11:19
 * @Version 1.0
 */
public class UdpServer {
    private final Bootstrap bootstrap;
    private final NioEventLoopGroup eventExecutors;

    public UdpServer() {
        eventExecutors = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new IdleStateHandler(0, 0, 60));
    }

    public void run() throws InterruptedException {
        int i = 0;
        Channel channel = bootstrap.bind(0).sync().channel();
        for (; ; ) {
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("hello client " + i, Charset.defaultCharset()), new InetSocketAddress("255.255.255.255", 25655))).sync();
            i++;
            Thread.sleep(2 * 1000);
        }
    }

    public void stop() {
        eventExecutors.shutdownGracefully();
    }

    public static void main(String[] args) {
        UdpServer udpServer = new UdpServer();
        try {
            udpServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            udpServer.stop();
        }
    }
}
