### ChannelPipeline 接口

channelPipeline 是一个拦截流经channel 的入站和出站时间的channel handler 实例链。每一个新建的channel 都将会被分配一个信的channel pipeline 这项关联是永久的。 channel 既不能附加另外一个channel pipeline ，也不能分离当前的。 

根据事件的起源，事件将会被channelinboundhandler 或者 channelOutboundHandler 处理，随后，通过调用 channel handler context 实现，它将被转发给同一个超类型的下一个channel handler 

#### channel handler context 

channel handler  context 使得channel handler  能够和它的channel pipeline 以及其他的channel handler 交互 。channel handler 可以通知其所属的channel pipeline 中的下一个channel handler ，甚至可以动态修改它所属的channel pipeline 



