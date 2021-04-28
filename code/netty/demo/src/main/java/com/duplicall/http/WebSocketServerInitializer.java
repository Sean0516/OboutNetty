package com.duplicall.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description WebSocketServerInitializer
 * @Author Sean
 * @Date 2021/4/28 17:09
 * @Version 1.0
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),
//                若请求的端点是 websocket 则处理该升级握手
                new WebSocketServerProtocolHandler("/websocket"),
//                TextFrame 处理器
                new TextFrameHandler(),
//                二进制处理器
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    }

    public final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            logger.info("text websocket msg [{}]", textWebSocketFrame.text());
        }
    }

    public final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
            logger.info("binary frame deal ");
        }
    }

    public final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
            logger.info("continuation frame msg [{}]", continuationWebSocketFrame.text());
        }
    }
}
