package com.duplicall;

import com.duplicall.client.NettyClient;
import com.duplicall.server.NettyServer;
import org.junit.Test;

/**
 * @Description NettyTest
 * @Author Sean
 * @Date 2021/4/7 11:13
 * @Version 1.0
 */
public class NettyTest {

    @Test
    public void testNettyServer(){
        NettyServer nettyServer=new NettyServer();
        try {
            nettyServer.start(20020);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testNettyClient(){
        NettyClient nettyClient=new NettyClient();
        try {
            nettyClient.startClient("192.168.12.232",20020);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
