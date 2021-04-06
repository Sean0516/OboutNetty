### Channel 

​	channel 是Java  NIO 的一个基本构造。 它代表一个到实体（如一个硬件设备，一个文件，一个网络套接字或者一个能够执行一个或多个不同的IO 操作的程序组件）的开发连接， 如读操作，写操作

​	可以把channel 看作是入站或出站数据的载体，因此，它可以被打开或者被关闭。 连接或者断开连接

### 回调 (ChannelHandlerAdapter)

​	netty 内部使用了回调来处理事件，当一个回调被触发时，相关的事件可以通过继承ChannelInboundHandlerAdapter类的重写方法来进行处理。 

```java
public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {
    public ChannelInboundHandlerAdapter() {
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelWritabilityChanged();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
```

### Future (ChannelFuture)

​	Future 提供了另一种在操作完成时通知应用程序的方式，这个对象可以看作是一个一部操作的结果的占位符，它将在未来的某个时刻完成，并提供对其结果的访问

​	Netty 提供了ChannelFuture ，用于在执行异步操作的时候使用，ChannelFuture 提供了几种额外的方法，这些方法使得我们能够注册一个或者多个ChannelFutureListener实例，监听器的回调方法operationComplete() 将会在对应的操作完成时被调用。 然后监听器可以判断该操作是成功完成了还是出错了。如果是后者，我们可以检索产生的 throw able 简而言之， ChannelFutureListener 提供的通知机制消除了手动检查对应的操作是否完成的必要

每个Netty 的出站 I/O 操作豆浆会发怒hi一个ChannelFuture 也就是说，他们不会阻塞。

```java
new ChannelFutureListener() {
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {

        }else {

        }
    }
});
```

### 事件和ChannelHandler

Netty 使用不同的事件来通知我们状态的改变或者是操作的状态，这使得我们能够基于已经发生的事件来出发适当的动作。

Netty 是一个网络编程框架，所有事件是按照他们与入站或出站数据流的相关性进行分类的。

######  由入站数据或相关的状态改变而触发的事件包括

- 连接已被激活或者连接失活
- 数据读取
- 用户事件
- 错误事件
- 

###### 出站事件未来将会出发的某个动作的操作结果，这些动作包括：

- 打开或关闭到远程节点的连接
- 将数据写入到或者冲刷到套接字

Netty 提供了大量预定义的可以开箱即用的Channel Handler 实现，包括用于各种协议（HTTP 和SSL /TLS ）的ChannelHandler 在内部，ChannelHandler 自己也使用了事件和Future ，使得他们也成为了应用程序将使用的相同抽象的消费者

