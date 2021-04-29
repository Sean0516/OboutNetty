package com.duplicall.http;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Description ChunkedWriteHandlerInitializer
 * @Author Sean
 * @Date 2021/4/29 11:33
 * @Version 1.0
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
//        添加ChunkedWriteHandler 用以处理作为ChunkedInput 传入的数据
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new CustomChunkedWriteStreamHandler());

    }

    public final class CustomChunkedWriteStreamHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
//            当连接建立 ，使用ChunkedStream 写数据
//            ctx.writeAndFlush(new ChunkedStream(new FileInputStream("sss")));
            ctx.writeAndFlush(new ChunkedFile(new File("sss")));
        }

    }
}
