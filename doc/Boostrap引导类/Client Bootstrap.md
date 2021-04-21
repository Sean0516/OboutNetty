客户端 Bootstrap 类

下面为Bootstrap 类的方法概述

| 名称                              | 描述                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| group(EventLoopGroup)             | 设置用于处理channel 所有事件的eventloopgroup                 |
| channel（channel）                | 用于指定channel的实现类。如果该类没有提供默认的构造函数，可以通过调用channelfactory 方法来指定一个工厂类，它将会被bind 方法调用 |
| localAddress（SocketAddress ）    | 指定channel 应该绑定到的本地地址，如果没有指定，则将由操作系统创建一个随机的地址，或者也可以通过bind 或者connect（） 方法指定localAddress |
| option(ChannelOption )            | 设置channelOption 其将被应用到每个新创建的channel 的channelConfig ，这些选项将会通过bind 或者 connect 方法设置到channel ，不管那个先被调用。 |
| attr（Attribute<T> key ,T value） | 指定新创建的channel的属性值，这些属性值通过bind  或者connect 方法设置到channel 的。 具体取决于谁先被调用。 |
| handler （ChannelHandler ）       | 设置将被添加到channelPipeLine 以接收事件通知的channelhandler |
| clone                             | 克隆当前的bootsreap 其具有和原始的Bootstrap 相同的设置信息   |
| remoteAddress(SocketAddress)      | 设置远程地址。 也可以通过connect方法来指定                   |
| connect                           | 连接到远程节点，并返回一个channelFutrue ，将其会在连接操作完成后接收到通知 |
| bind                              | 绑定channel 并返回一个channelFuture ，其将会绑定操作完成后接收到通知。在哪之后必须调用connect 方法来建立连接 |

```java
 public void startClient(String host, int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//        创建一个Boostrap 实例用以创建和连接新的客户端channel
        Bootstrap bootstrap = new Bootstrap();
//        设置EventLoopGroup 提供用于处理channel 事件的EventLoop
        bootstrap.group(eventLoopGroup)
//                指定要使用的channel 实现
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .option(ChannelOption.SO_KEEPALIVE,true)
//                设hi在channelHandler
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new CustomClientOutHandler())
                                .addLast(new CustomClientHandler());
                    }
                });
        try {
//            连接到远程主机
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
```

#### 注意

在Boostrap 调用bind 或者 connect 方法之前，必须调用一下方法来设置所需组件

- group
- channel 或者  channelFactory
- hanndler 

如果不这样做，则会导致异常。其中，对handler 方法的调用尤其重要，因为它需要配置好 channelPipeline



