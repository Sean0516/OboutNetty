

### EventLoop 接口

Netty  的EventLoop 是协同设计的一部分， 他采用了两个基本的API  并发和网络编程。 io.netty.util.concureent 包构建在JDK 的java.util.concurrent 包上，用来提供线程执行器。 其次，io.netty.channel 包中的类，用于 channel  的事件进行交互

一个EventLoop 将由一个永远都不会改变的Thread  驱动， 同时 任务 Runnable 或者 Callable 可以直接交给 EventLoop 实现。 根据配置和可用核心的不同，可能会创建多个 EventLoop 实例用以优化资源的使用。 并且单个EventLoop 可能会被指派用于服务多个 Channel 

### EventLoop 调度任务

由于ScheduledExecutorService 的实现具有局限性。 Netty 通过Channel 的EventLoop 实现任务调度解决了这一问题。 、

### EventLoop 的实现细节

1. #### 线程管理

   Netty 线程模型的卓越性取决于对于当前执行的Thread 的身份确定。确定他是否是分配给当前channel 以及他的 EventLoop 的那一个线程 

   如果当前调用线程正是支撑 EventLoop 的线程，那么所提交的代码块将会被直接执行。 否则， EventLoop 将调度该任务以便售后执行，并将他放入到内部队列中。当EventLoop 下次处理它的事件时，它会执行队列中的那些任务/事件。 这也就解释了任何的Thread 是如何直接和 Channel 直接交互而无需在ChannelHandler 中进行额外同步的

   需要注意的是，每个EventLoop 都有它自己的任务队列，独立于任何的EventLoop 。下面是EventLoop 用于调度任务的执行逻辑

   ![image-20210421161151135](D:\study\OboutNetty\doc\EventLoop\EventLoop.jpg)

2. #### 线程分配

   1. 异步传输

      异步传输实现只使用了少量的EventLoop （以及和它们相关联的Thread） 而且在当前的线程模型中，它们可能会被多个 channel 所共享，这使得可以通过尽可能少的thread来支撑大量的channel。 而不是每个channel 分配一个thread

      EventLoopGroup 负责为每个新建的channel 分配EventLoop ，在当前实现中，使用顺序循环的方式进行分配以获取一个均衡的分布，并且相同的EventLoop 可能会被 分配多个channel 

      一旦一个channel 被分配给一个EventLoop ，它将在它的整个生命周期中都使用这个event loop 。

      需要注意eventLoop 的分配方式对 threadLocal 的使用的影响。因为一个EventLoop 通常会被用于支持多个channel ，所以对于所有相关联的channel来说 ，threadLocal 都是一样的。 这使得它对于实现状态追踪等功能来说是个糟糕的选择

   2. 阻塞传输

      保证每个channel 的I/O 事件都将会被一个Thread 处理

```java
ctx.channel().eventLoop().execute(() -> logger.info("do event loop "))
```





