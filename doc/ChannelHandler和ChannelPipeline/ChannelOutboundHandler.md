出站操作和数据将由ChannelOutboundHandler 处理。它的方法将被Channel ChannelPipeline 以及 channelHandlerContext 调用

hannelOutboundHandler  的一个强大的功能是可以按需推迟操作或者事件。 这使得可以通过一些复杂的方法来处理请求

| 类型                                                         | 描述                                              |
| ------------------------------------------------------------ | ------------------------------------------------- |
| bind(ChannelHandlerContext, SocketAddress,ChannelPromise)    | 当前请求将channel 绑定到本地地址时被调用          |
| connect（ChannelHandlerContext, remote SocketAddress, local SocketAddress,ChannelPromise） | 当前请求将channel 连接到远程节点时被调用          |
| disconnect（ChannelHandlerContext ，ChannelPromise）         | 当请求将channel 从远程节点断开时被调用            |
| close （ChannelHandlerContext ，ChannelPromise）             | 当请求关闭channel时被调用                         |
| deregister （ChannelHandlerContext ，ChannelPromise）        | 当请求将channel 从它的event loop 注销时被调用     |
| read （ChannelHandlerContext ）                              | 当请求从channel 读取更多的数据时被调用            |
| flush （ChannelHandlerContext）                              | 当请求通过channel将队列数据冲刷到远程节点时被调用 |
| write（ChannelHandlerContext ，Object,ChannelPromise）       | 当请求通过channel 数据写到远程节点时被调用        |



ChannelPromise 与channelFuture  ChananelOutboundHandler 中的大部分方法都需要一个channelPromise 参数，以便在操作完成时得到通知。channelPromise 是channelFuture 的一个子类。 其定义了一些可写的方法。 如setSuccess 和 setFailure 从而使 channelFuture 不可变

```java
public class CustomClientOutHandler extends ChannelOutboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        logger.info("remote addr [{}]  connect success promise [{}]", remoteAddress.toString(), promise.isSuccess());
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        logger.info("local addr bind [{}] ", localAddress.toString());
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("dis connect [{}]", ctx.channel().remoteAddress().toString());
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel close ");
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("channel de register ");
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("msg [{}]", msg);
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
```