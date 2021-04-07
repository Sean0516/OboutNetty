Netty 服务器的基本代码组件

- ChannelInboundHandlerAdapter 业务类逻辑实现
  1. channelRead()  --- 对于每个传入的消息都要调用
  2. channelReadComplete() ---- 通知ChannelInboundHandler 最后一次对chanelRead() 的调用， 是当前批量读取中的最后一条信息
  3. exceptionCaught ()  在读取操作期间，有异常抛出时会调用 (重写exceptionCaught 方法允许对throw able 的任何子类型做出反应)
  
  [^如果不捕获异常会发生什么]: 每个channel 都拥有一个与之关联的channel pipeline ，其持有一个channel handler 的实例链，在默认情况下，channel handler 会把对他的方法的调用转发给链中的下一个channel handler 。因此，如果exceptionCaught 方法没有被该链中的某处实现，那么所接收的异常浆会被传递到channel pipeline 的尾端并被记录。为此，程序应该提供至少一个实现了exceptionCaught 方法的channel handler 。
  
  
  
  ```java
  
  /**
   * 标记一个channel handler 开业被多个channel 安全地共享
   */
  @ChannelHandler.Sharable
  public class CustomServerHandler extends ChannelInboundHandlerAdapter {
      private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
      /**
       * 获取接收的消息
       *
       * @param ctx
       * @param msg
       * @throws Exception
       */
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          ByteBuf in = (ByteBuf) msg;
          logger.info("received msg [{}]", in.toString(CharsetUtil.UTF_8));
          ctx.write(in);
      }
  
      /**
       * 将未决消息冲刷到远程节点，并且关闭该 Channel
       *
       * @param ctx
       * @throws Exception
       */
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListeners(ChannelFutureListener.CLOSE);
      }
  
      /**
       * 打印异常 并关闭channel
       * @param ctx
       * @param cause
       * @throws Exception
       */
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          cause.printStackTrace();
          logger.error("error [{}]", cause.getMessage(), cause);
          ctx.close();
      }
  }
  ```
  
- 创建一个ServerBootStrap 实例来引导和绑定服务器

- 创建并分配一个 NioEventLoopGroup 实例来进行事件的处理。如接收新的连接以及读写数据

- 指定服务器绑定的本地的 IntetSocketAddress

- 使用ChannelInboundHandlerAdapter 业务类的实例 ，初始化每一个新的channel

- 调用serverBootStrap.bind() 方法绑定服务器

```java

public class NettyServer {
    public void start(int port) throws InterruptedException {
//        初始化自定义的channel handler
        CustomServerHandler customServerHandler = new CustomServerHandler();
//        创建EventLoopGroup
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
//        创建ServerBootstrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(eventExecutors)
//                指定所使用的NIO 传输channel
                .channel(NioServerSocketChannel.class)
//                指定端口,设置套接字地址
                .localAddress(new InetSocketAddress(port))
//                添加自定义的Chanel handler 到子channel 的 channel pipeline .
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        自定义的channel handler 被标注为 @shareable ,所以所有的客户端连接,都会使用同一个Custom Channel Handler
                        socketChannel.pipeline().addLast(customServerHandler);
                    }
                });

        try {
//            异步绑定服务器,调用sync方法阻塞,等待直到绑定完成
            ChannelFuture future = serverBootstrap.bind().sync();
//            获取channel 的close future ,并且阻塞当前线程直到他完成.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        }finally {
//            关闭EventLoopGroup ,释放所有资源
            eventExecutors.shutdownGracefully().sync();
        }

    }
}
```

