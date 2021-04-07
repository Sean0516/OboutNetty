### 通过channel handler 实现客户端逻辑

客户端也会有一个用来处理数据的channelInboundHandler 。在这个场景下，可以通过扩展 SimpleChannelInboundHandler 类来处理所有必须的任务。要求重写下面的方法

- channelActive  ----- 在到服务器的连接已经建立之后将被调用
- channelRead0() --- 当从服务器接收到一条消息时调用
- exceptionCaught() --- 在处理过程中引起异常时被调用

```java
@ChannelHandler.Sharable
public class CustomClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /**
     * 当被通知 channel是活跃的时候发送一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Netty Server", CharsetUtil.UTF_8));
    }

    /**
     * 打印服务端发送的消息
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        String addr = channelHandlerContext.channel().localAddress().toString();
        logger.info("client received addr [{}], msg [{}]",addr,byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 发生异常时,记录错误并关闭 (可以考虑重连)
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("server error as [{}]",cause.getMessage(),cause);
        ctx.close();
    }
}
```

#### SimpleChannleInboundHandler 和 ChanelInboundHandler

在客户端，当channelRead0()方法完成时，你已经有了传入消息，并且已经处理完了他。当该方法返回时，simpleChannelInboundHandler 负责释放指向报错该消息的ByteBuf 的内存引用

在服务端中。当需要将消息发送给发送者，而write 操作是异步的。直到channelRead 方法返回后可能仍然没有完成。 因此服务端扩展了ChannelInboundHandlerAdapter 其在这个时间点不会释放消息

消息在channelReadComplete方法中，当writeAndFlush 方法被调用时被释放。

### Client 客户端创建流程

- 初始化客户端，创建一个BootStrap 实例
- 为进行时间分配一个NioEventLoopGroup ，其中时间处理包括创建新的连接以及处理入站和出站数据
- 为服务器连接创建一个InetSocketAddress 实例
- 当连接被建立时，一个CustomClientHandler 实例会被安装到该Channel 的ChannelPipeline 中
- 在一切都设置完成后，调用boostrap.connnet 连接远程节点

```java
public class NettyClient {
    public void startClient(String host, int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new CustomClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
```