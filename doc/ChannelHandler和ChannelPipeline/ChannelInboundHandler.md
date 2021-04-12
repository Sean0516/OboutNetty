ChannelInboundHandler 接口的生命周期方法

| 类型                      | 描述                                                         |
| ------------------------- | ------------------------------------------------------------ |
| channelRegistered         | 当channel 已经注册到它的event loop 并且能够处理 IO 的时候被调用 |
| channelUnRegistered       | 当channel 从它的event loop 注销并且无法处理任何IO时被调用    |
| channelActive             | 当channel 处于活跃状态时被调用，channel已经连接/绑定并且已经就绪 |
| channelInactive           | 当channel离开活动状态并且不再连接它的远程节点时被调用        |
| ChanelReadComplete        | 当channel 上的一个读操作完成时被调用                         |
| channelRead               | 当从channel 读取数据时被调用                                 |
| channelWritabilityChanged | 当channel的可写状态发生改变时被调用，用户可以确保写操作不会完成得太空 |
| userEventTriggered        | 当channelInboundHandler.fireUserEventTriggered方法被调用时被调用。 因为一个POJO被传进了ChannelPipeline |

