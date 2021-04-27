package com.duplicall.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @Description CustomMessageToMessageEncoder
 * @Author Sean
 * @Date 2021/4/26 17:17
 * @Version 1.0
 */
public class CustomMessageToMessageEncoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
