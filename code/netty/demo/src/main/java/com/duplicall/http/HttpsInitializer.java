package com.duplicall.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @Description HttpsInitializer
 * @Author Sean
 * @Date 2021/4/27 14:41
 * @Version 1.0
 */
public class HttpsInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;
    private final boolean client;

    public HttpsInitializer(SslContext context, boolean startTls, boolean client) {
        this.context = context;
        this.startTls = startTls;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine sslEngine = context.newEngine(channel.alloc());
        pipeline.addFirst("ssl", new SslHandler(sslEngine, startTls));
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }
}
