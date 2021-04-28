### WebSocketFrame 类型

| 名称                       | 描述                                                         |
| -------------------------- | ------------------------------------------------------------ |
| BinaryWebSocketFrame       | 数据帧： 二进制数据                                          |
| TextWebSocketFrame         | 数据帧： 文本数据                                            |
| ContinuationWebSocketFrame | 数据帧： 属于上一个BinaryWebSocketFreom 或者TextWebSocketFrame  的文本或二进制数据 |
| CloseWebSocketFrame        | 控制帧： 一个CLOSE 请求，关闭的状态码以及关闭的原因          |
| PingWebSocketFrame         | 控制帧： 请求一个PongWebSocketFrame                          |
| PongWebSocketFrame         | 控制帧： 对PingWebSocketFrame 请求响应                       |

```java
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),
                new WebSocketServerProtocolHandler("websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    }

    public final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            logger.info("text websocket msg [{}]", textWebSocketFrame.text());
        }
    }

    public final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
            logger.info("binary frame deal ");
        }
    }

    public final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
            logger.info("continuation frame msg [{}]", continuationWebSocketFrame.text());
        }
    }
}
```

### 保护WebSocket 

要想为 WebSocket  添加安全性， 只需要将 SslHandler 作为第一个ChannelHandler  添加到ChannelPipeline 

