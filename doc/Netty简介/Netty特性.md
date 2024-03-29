### Netty 的特性

##### 设计

- 统一的API 支持多种传输类型，阻塞和非阻塞的
- 简单而强大的线程模型
- 真正的无连接数据报套接字支持
- 链接逻辑组件以支持复用

#### 易于使用

- 详实的java doc 和大量的实例集
- 不需要超过JDK1.6 )+ 的依赖

#### 性能

- 拥有比JAVA 的核心API 更高的吞吐量以及更低的延迟
- 得益于池化和服用，拥有更低的资源消耗
- 最少的内存复制

#### 健壮性

- 不会因为慢速，快速或者超载的连接而导致 outofmemoryError 
- 消除在高速网络中NIO应用程序常见的不公平读/写比率

#### 安全性

- 完整的SSL/TLS 以及 StartTLS 支持
- 可用于受限环境下 比如 Applet 和OSGI

#### 社区驱动

- 发布快速且频繁



