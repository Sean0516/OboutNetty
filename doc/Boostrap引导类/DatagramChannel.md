### DatagramChannel

DatagramChannel  提供UDP 协议的编程，唯一的区别在于不再调用connect ，而是只调用bind 方法

```java
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new OioEventLoopGroup()).channel(OioDatagramChannel.class)
            .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                @Override
                protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                    System.out.println("datagramPacket.toString() = " + datagramPacket.toString());
                }
            });
    ChannelFuture bind = bootstrap.bind(new InetSocketAddress(40000));
    bind.addListener((ChannelFutureListener) channelFuture -> {
        if (channelFuture.isSuccess()) {
            System.out.println("\"success\" = " + "success");
        } else {
            System.out.println("\"bind failed \" = " + "bind failed ");
            channelFuture.cause().printStackTrace();
        }
    });
}
```

