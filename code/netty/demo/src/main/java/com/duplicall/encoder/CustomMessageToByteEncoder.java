package com.duplicall.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @Description CustomMessageToByteEncoder
 * @Author Sean
 * @Date 2021/4/26 17:08
 * @Version 1.0
 */
public class CustomMessageToByteEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    }
}
