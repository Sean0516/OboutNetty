package com.duplicall.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;

/**
 * @Description HttpPipelineInitializer
 * @Author Sean
 * @Date 2021/4/27 13:45
 * @Version 1.0
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client) {
//            客户端添加HttpResponseDecoder来处理来自服务器的响应
//            添加 HttpRequestEncoder 以向服务器发送请求
            pipeline.addLast("decoder", new HttpResponseDecoder());
            pipeline.addLast("encoder", new HttpRequestEncoder());
        } else {
//            服务端添加HttpResponseEncoder 向客户端发送响应
//            添加HttpRequestDecoder 解码服务端发送的请求
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
//             设置最大的消息大小 ,以及添加HttpObjectAggregator
            pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));

        }
    }
}
