package com.duplicall.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @Description CustomMessageToMessageCodec
 * @Author Sean
 * @Date 2021/4/26 17:44
 * @Version 1.0
 */
public class CustomMessageToMessageCodec extends MessageToMessageCodec<Integer, String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        list.add(Integer.valueOf(s));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
