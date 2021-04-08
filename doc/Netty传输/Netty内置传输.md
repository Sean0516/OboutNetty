Netty 内置了一些可开箱即用的传输，因为并不是所有的传输都支持每一种协议，所以必须选择一个和应用程序使用的协议相容的传输

| 名称     | 包                          | 描述                                                         |
| -------- | --------------------------- | ------------------------------------------------------------ |
| NIO      | io.netty.channel.socket.nio | 使用Java.nio.channels 包作为基础----基于选择器的方式         |
| Epoll    | io.netty.channel.epoll      | 由JNI 驱动的epoll() 和非阻塞IO，这个传输支持只有在Linux上可用的多种特性。如SO_REUSEPORT, 比NIO传输更快，而且是完全非阻塞的 |
| OIO      | io.netty.channel.socket.oio | 使用Java.net 包作为基础 ---- 使用阻塞流                      |
| Local    | io.netty.channel.local      | 可以在VM 内部通过管道进行通信的本地传输                      |
| Embedded | io.netty.channel.embedded   | Embedded传输，允许使用channelhandler而又不需要一个真正的基于网络的传输，这个在测试channelhandler 实现时非常有用 |

### NIO 非阻塞IO

NIO 提供了一个所有IO操作的全异步的实现，它使用了JDK 基于选择器的NIO

选择器背后的基本概念是充当一个注册表，在哪里你将可以请求在channel的状态发生变化时得到通知。可能发生的状态变化有

- 新的channel已被接收并且就绪
- channel 连接已经完成
- channel 有已经就绪的可供读取的数据
- channel 可用于写数据

选择器允许在一个检查状态变化并对其做出相应响应的线程上，在应用程序对状态的改变做出响应后，选择器将会被重置， 并重复这个操作

### Epoll  用于Linux 的本地非阻塞传输

Netty为Linux提供了一组NIO API，其以一种和它本身的设计更加一致的方式使用epoll，并且以一种更加轻量的方式使用中断。如果你的应用程序旨在运行于Linux系统，那么请考虑利用这个版本的传输；你将发现在高负载下它的性能要优于JDK的NIO实现

如果需要使用Epoll  只需要替换 EpollEventLoopGroup  和 EpollServerSocketChannel

### OIO 旧的阻塞IO

Netty 的 OIO 传输实现代表了一种折中：它可以通过常规的传输 API 使用，但是由于它是建立在 java.net 包的阻塞实现之上的，所以它不是异步的。但是，它仍然非常适合于某些用途

### 用于JVM 内部通信的Local 传输

netty 提供了一个Local 传输 ，用于在同一个JVM 中运行的客户端和服务器之间的异步通信。

### Embedded 传输

Netty 提供了一种额外的传输，使得你可以将一组 ChannelHandler 作为帮助器类嵌入到其他的 ChannelHandler 内部。通过这种方式，你将可以扩展一个 ChannelHandler 的功能，而又不需要修改其内部代码

