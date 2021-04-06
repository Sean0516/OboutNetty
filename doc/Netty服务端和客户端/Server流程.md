Netty 服务器的基本代码组件

- ChannelInboundHandlerAdapter 业务类逻辑实现
  1. channelRead()  --- 对于每个传入的消息都要调用
  2. channelReadComplete() ---- 通知ChannelInboundHandler 最后一次对chanelRead() 的调用， 是当前批量读取中的最后一条信息
  3. exceptionCaught ()  在读取操作期间，有异常抛出时会调用
- 创建一个ServerBootStrap 实例来引导和绑定服务器
- 创建并分配一个 NioEventLoopGroup 实例来进行事件的处理。如接收新的连接以及读写数据
- 指定服务器绑定的本地的 IntetSocketAddress
- 使用ChannelInboundHandlerAdapter 业务类的实例 ，初始化每一个新的channel
- 调用serverBootStrap.bind() 方法绑定服务器

