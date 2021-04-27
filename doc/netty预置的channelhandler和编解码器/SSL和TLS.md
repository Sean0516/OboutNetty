netty 通过SslHandler 的channelhandler 实现SSL/TLS  ，其中SslHandler  内部使用SSLEngine 来完成实际的工作

### netty 的openSSL 和SSLEngine 的实现

netty 使用openSSL 工具包和 JDK 提供的SSLEngine 两种方式实现。其中， openSSL 的性能比JDK 的更好

如果OpenSSL 库可用，可以将Netty 程序配置为默认使用OpenSslEngine ，如果不可用，netty 将会使用JDK 来实现

###  SslHandler 加解密的流程

1. SslHandler 拦截了加密的入站数据
2. SslHandler 对数据进行解密，并将他定向到入站端
3. 出站数据被传递通过SslHandler
4. SslHandler 对数据进行了加密，并且春娣给出站端



### 添加SSL 和TLS 支持

```java
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    /**
     * 如果设置为true ，第一个写入的消息将不会被加密
     */
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
//        对于每个SslHandler实例，都使用 channel 的byteBuf allocator 从 sslContext 获取新的SSLEngine
        SSLEngine sslEngine = context.newEngine(channel.alloc());
//        将SslHandler 作为第一个 channel handler  添加到 channel pipeline 
        channel.pipeline().addFirst("ssl", new SslHandler(sslEngine, startTls));
    }
}
```

