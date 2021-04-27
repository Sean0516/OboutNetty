### 添加HTTP 支持

```
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client){
//            客户端添加HttpResponseDecoder来处理来自服务器的响应
//            添加 HttpRequestEncoder 以向服务器发送请求
            pipeline.addLast("decoder",new HttpResponseDecoder());
            pipeline.addLast("encoder",new HttpRequestEncoder());
        }else {
//            服务端添加HttpResponseEncoder 向客户端发送响应
//            添加HttpRequestDecoder 解码服务端发送的请求
            pipeline.addLast("decoder",new HttpRequestDecoder());
            pipeline.addLast("encoder",new HttpResponseEncoder());

        }
    }
}
```

### 聚合HTTP 消息

由于 HTTP 请求和响应可能由许多部分组成，因此你需要聚合他们以形成完成的消息。 netty 提供了一个聚合器，它可以将多个消息部分合并为FullHttpRequest 或者 FullHttpResponse 消息。 通过这样的方式，你总会看到完整的消息内容

由于消息分段需要被缓冲，直到可以转发一个完整的消息给下一个channelInboundHandler ，所以，这个操作有轻微的开销，其好处是不需要关心消息碎片

```java
//             设置最大的消息大小 ,以及添加HttpObjectAggregator
            pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
```

### HTTP 压缩

当使用HTTP 时，建议开启压缩功能以尽可能地减小传输数据的大小。 虽然压缩会带来一些CPU 时钟周期上的开销，但是通常来说是一个可行的方案。 Netty 为压缩和解压缩提供了ChannelHandler 实现，他们同时支持gzip 和deflate 编码

```java
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpCompressionInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client){
//           使用HttpContentDecompressor 处理服务端的压缩内容
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("decompressor",new HttpContentDecompressor());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
//            使用 HttpContentCompressor 压缩数据
            pipeline.addLast("compressor",new HttpContentCompressor());
        }
    }
}
```

### 使用Https

要启用https  只需要将 SslHandler 添加到 channel handler 中

```java
public class HttpsInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;
    private final boolean client;

    public HttpsInitializer(SslContext context, boolean startTls, boolean client) {
        this.context = context;
        this.startTls = startTls;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine sslEngine = context.newEngine(channel.alloc());
        pipeline.addFirst("ssl", new SslHandler(sslEngine, startTls));
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }
}
```

