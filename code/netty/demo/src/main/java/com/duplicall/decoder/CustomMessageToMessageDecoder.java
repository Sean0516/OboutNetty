package com.duplicall.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @Description CustomMessageToMessageDecoder
 * @Author Sean
 * @Date 2021/4/26 16:54
 * @Version 1.0
 */
public class CustomMessageToMessageDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
