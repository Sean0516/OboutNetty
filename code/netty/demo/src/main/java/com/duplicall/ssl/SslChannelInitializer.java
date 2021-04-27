package com.duplicall.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @Description SslChannelIntitializer
 * @Author Sean
 * @Date 2021/4/27 9:56
 * @Version 1.0
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    /**
     * 如果设置为true ，第一个写入的消息将不会被加密
     */
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
//        对于每个SslHandler实例，都使用 channel 的byteBuf allocator 从 sslContext 获取新的SSLEngine
        SSLEngine sslEngine = context.newEngine(channel.alloc());
//        将SslHandler 作为第一个 channel handler  添加到 channel pipeline
        channel.pipeline().addFirst("ssl", new SslHandler(sslEngine, startTls));
    }
}
