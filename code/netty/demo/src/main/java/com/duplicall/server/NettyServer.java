package com.duplicall.server;

import com.duplicall.handler.CustomServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description NettyServer
 * @Author Sean
 * @Date 2021/4/7 9:40
 * @Version 1.0
 */
public class NettyServer {
    public void start(int port) throws InterruptedException {
//        初始化自定义的channel handler
        CustomServerHandler customServerHandler = new CustomServerHandler();
//        创建EventLoopGroup
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
//        创建ServerBootstrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(eventExecutors)
//                指定所使用的NIO 传输channel
                .channel(NioServerSocketChannel.class)
//                指定端口,设置套接字地址
                .localAddress(new InetSocketAddress(port))
//                添加自定义的Chanel handler 到子channel 的 channel pipeline .
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        自定义的channel handler 被标注为 @shareable ,所以所有的客户端连接,都会使用同一个Custom Channel Handler
                        socketChannel.pipeline().addLast(customServerHandler);
                    }
                });

        try {
//            异步绑定服务器,调用sync方法阻塞,等待直到绑定完成
            ChannelFuture future = serverBootstrap.bind().sync();
//            获取channel 的close future ,并且阻塞当前线程直到他完成.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        }finally {
//            关闭EventLoopGroup ,释放所有资源
            eventExecutors.shutdownGracefully().sync();
        }

    }
}
