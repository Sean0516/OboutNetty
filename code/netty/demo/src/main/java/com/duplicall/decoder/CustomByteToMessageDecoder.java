package com.duplicall.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @Description CustomByteToMessageDecoder
 * @Author Sean
 * @Date 2021/4/26 15:42
 * @Version 1.0
 */
public class CustomByteToMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >=4) {
            int readableBytes = byteBuf.readableBytes();
            if (byteBuf.readableBytes()>102400){
//                跳过所有的可读字节，并抛出异常
             byteBuf.skipBytes(readableBytes)   ;
             throw new TooLongFrameException("byte buf readable byte over 102400");
            }
            list.add(byteBuf.readInt());
        }
    }

}
