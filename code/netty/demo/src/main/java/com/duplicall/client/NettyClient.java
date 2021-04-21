package com.duplicall.client;

import com.duplicall.handler.CustomClientHandler;
import com.duplicall.handler.CustomClientOutHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description NettyClient
 * @Author Sean
 * @Date 2021/4/7 10:51
 * @Version 1.0
 */
public class NettyClient {
    public void startClient(String host, int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//        创建一个Boostrap 实例用以创建和连接新的客户端channel
        Bootstrap bootstrap = new Bootstrap();
//        设置EventLoopGroup 提供用于处理channel 事件的EventLoop
        bootstrap.group(eventLoopGroup)
//                指定要使用的channel 实现
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .option(ChannelOption.SO_KEEPALIVE,true)
//                设hi在channelHandler
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new CustomClientOutHandler())
                                .addLast(new CustomClientHandler());
                    }
                });
        try {
//            连接到远程主机
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
