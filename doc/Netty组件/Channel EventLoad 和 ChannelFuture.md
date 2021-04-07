### Channel (Socket)

基本的I/O 操作 bind() connect() read() write ()  . 依赖于底层网络传输所提供的原语。在基于Java的网络编程中，其基本的构造是class socket 。 netty 的channel 接口所提供的API ，大大降低了直接使用socket 类的复杂性。 此外 channel 也拥有许多预定义的，专门化实现的广泛类层次结构的根。 

- EmbeddedChannel
- LocalServerChannel 
- NioDatagramChannel
- NioSctpChannel
- NioSocketChannel



### EventLoop (控制流，多线程处理，并发)

​	EventLoop 定义了Netty 的核心抽象，用于处理连接的生命周期所发生的事情。

#### channel  EventLoop 之间的关系

创建channel --》 将channel注册到 EventLoop  ---》 在整个生命周期内部，使用EventLoop 处理IO事件

- 一个EventLoopGroup 包含一个或多个EventLoop
- 一个EventLoop  在他的生命周期中，之和一个Thread  绑定
- 所有由EventLoop 处理的IO 时间都将在他专有的Thread 被处理
- 一个channel 在他的生命周期内只注册于一个EventLoop
- 一个EventLoop 可能会被分配给一个或多个Channel 



### ChannelFuture (异步通知)

由于Netty 中所有的IO 操作都是异步的。因为一个操作可能不会立即返回。所以，我们需要一种用于在之后的某个时间点缺点其结果的方法。 为此，netty  提供了channelFuture 接口，其addListener() 方法注册了 ChnnelFutureListenner . 以便在某个操作完成时 得到通知
