package com.duplicall.future;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description CustomChannelFutureListener
 * @Author Sean
 * @Date 2021/4/27 9:52
 * @Version 1.0
 */
public class CustomChannelFuture implements GenericFutureListener<ChannelFuture> {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()){
            logger.info("return success ");
        }else {
            logger.info("return failed ");
        }
    }
}
