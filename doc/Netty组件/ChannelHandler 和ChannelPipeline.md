### ChannnelHandler 接口

Netty 主要组件是ChannelHandler ，他充当了所有处理入站和出站数据的应用程序逻辑的容器。ChannelHandler 可专门用于几乎任何类型的动作。例如将数据从一种格式转换为另外一种格式。或者处理转换过程中所抛出的异常

### 为什么需要ChannnelHandler 适配器类

有一些适配器类可以将编写自定义的channelhandler 所需要的努力降低到最低限度。 因为他们提供了定义在对应接口中的所有方法的默认实现

下面这些是编写自定义channelhandler 经常会用到的适配器类

- channelhandleradapter
- channelInboundHandlerAdapter
- ChannelOutBoundHandlerAdapter
- ChannelDuplexHandler
- 



### ChannelPipwline 接口

ChannelPipeline 提供了ChannelHandler 链容器，并定义了用于在该链上传播入站和出站事件流的API 。当Channel 被创建时，他会被自动分配到他专属的ChannelPipeline 。 

ChannelHandler 安装到ChannelPipeline 中的过程如下所示：

- 一个channelInitializer 的实现被注册到ServerBootStrap 中
- 当ChannelInitializer.initChannel 方法被调用时，channelinitializer 将channelpipeline 中安装一组自定义的channelhandler
- channelinitializer 将他自己从channelpipeline 中溢移除