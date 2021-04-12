**ChannelHandler 的生命周期方法**

| 类型            | 描述                                                |
| --------------- | --------------------------------------------------- |
| handlerAdded    | 当把channelHandler 添加到ChannelPipeline 中时被调用 |
| handlerRemoved  | 当从channelPipeline 中移除channelHandler 时被调用   |
| exceptionCaught | 当处理过程中在channelPipeline 中有错误产生时被调用  |

Netty 定义了下面两个重要的ChannelHandler 子接口：

- ChannelInboundHandler----- 处理入站数据以及各种状态变化
- ChannelOutboundHandler -----处理出站数据并且允许拦截所有的操作

