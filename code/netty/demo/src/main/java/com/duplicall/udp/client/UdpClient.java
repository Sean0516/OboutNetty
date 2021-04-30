package com.duplicall.udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @Description UdpClient
 * @Author Sean
 * @Date 2021/4/30 11:30
 * @Version 1.0
 */
public class UdpClient {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public UdpClient(InetSocketAddress address) {
    group =new NioEventLoopGroup();
    bootstrap=new Bootstrap();
    bootstrap.group(group)
            .channel(NioDatagramChannel.class)
            .option(ChannelOption.SO_BROADCAST,true)
            .handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new ClientHandler());
                }
            })
            .localAddress(address);
    }
    public Channel bind(){
        return bootstrap.bind().syncUninterruptibly().channel();
    }
    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        UdpClient udpClient = new UdpClient(new InetSocketAddress(25655));
        try {
            Channel bind = udpClient.bind();
            bind.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            udpClient.stop();
        }
    }
}
