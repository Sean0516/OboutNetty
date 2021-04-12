### Channel 的生命周期

接口 Channel  定义了一组和ChannelInboundHandler API 密切相关的简单但功能强大的状态模型。下表是channel的四个状态

**channel 的生命周期状态**



| 状态                | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| ChannelUnregistered | channel 已经被建立，但是还未注册到event loop                 |
| ChannelRegistered   | channel 已经被注册到event loop                               |
| channel active      | channel 处于活跃状态（已经连接到远程节点） 可以接收和发送数据 |
| ChannelInactive     | channel 没有连接到远程节点                                   |

channel的正常生命周期如下：

channelRegustered---> ChannelActive---->ChannelInActiver ----->ChannelUnregistered