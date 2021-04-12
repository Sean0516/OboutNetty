### 按需分配  ByteBufAllocator 接口

为了降低分配和释放内存的开销。Netty 通过 interface ByteBufAllocator 实现了 ByteBuf 的池化，它可以用来分配我们所描述的任意类型的ByteBuf 实例。 使用池化是特定应用程序决定的。其并不会以任何方式改变ByteBuf API

#### ByteBufAllocator  的方法

- buffer（）  返回一个基于堆或者直接内存存储的ByteBuf
- heapBuffer() 返回一个基于堆内存存储的ByteBuf
- directBuffer() 返回一个基于直接内存存储的ByteBuf
- compositeBuffer()  返回一个可以通过添加到最大指定数目的基于堆或者直接内存存储的缓冲区来扩展的CompositeByteBuf
- ioBuffer 返回一个用于套接字的IO 操作的ByteBuf

Netty 提供了两种ByteBufAllocatior 的实现： PooledByteBufAllocatior  和 UnpooledByteBufAllocatior  前者池化了 ByteBuf 的实例以提高性能并最大限度地减少内存碎片。 后者不池化ByteBUf 实例，并且每次被调用都会返回一个新的实例

Netty 默认使用了PooledByteBufAllocatior  ，但是可以通过ChannelConfig 来修改



### Unpooled 缓冲区

在某些情况下，可能无法获取到一个 ByteBufAllocator 的引用。对于这种情况，Netty 提供了一个简单的称为 Unpooled 的工具类，它提供了静态的辅助方法来创建未池化的ByteBuf  实例。 以下是 Unpooled 的方法

- buffer()  返回一个未池化的基于堆内存存储的ByteBuf
- directBuffer() 返回一个未池化的基于直接内存存储的ByteBuf
- wrappedBuffer() 返回一个包装了给定数据的ByteBuf
- copiedBuffer() 返回一个复制了给定数据的ByteBuf

### ByteBufUtil 类

ByteBufUtil 提供了用于操作 ByteBuf 的静态的辅助方法。因为这个 API 是通用的，并且和池化无关，所以这些方法已然在分配类的外部实现