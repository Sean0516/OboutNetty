package com.duplicall.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description HttpCompressionInitializer
 * @Author Sean
 * @Date 2021/4/27 14:34
 * @Version 1.0
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpCompressionInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client){
//           使用HttpContentDecompressor 处理服务端的压缩内容
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("decompressor",new HttpContentDecompressor());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
//            使用 HttpContentCompressor 压缩数据
            pipeline.addLast("compressor",new HttpContentCompressor());
        }
    }
}
