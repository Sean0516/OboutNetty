Netty 的数据处理API 通过两个组件暴露----- abstract class  ByteBuf 和 interface  ByteBufHolder 

下面是一些ByteBuf API 的优点

- 可以被用户自定义的缓冲区类型扩展
- 通过内置的复合缓冲区类型实现了透明的零拷贝
- 容量可以按需增长
- 在读和写着两种模式之间切换不需要调用ByteBuffer 的 flip 方法
- 读和写使用了不同的索引
- 支持方法的链式调用
- 支持引用计数
- 支持池化

