HTTP 是基于请求/响应模式的： 客户端向服务端发送一个HTTP 请求，然后服务端会返回一个HTTP 响应 。 Netty 提供了多种编码器和解码器以简化对HTTP 协议的使用

#### HTTP 编码器和解码器

| 名称                 | 描述                                                         |
| -------------------- | ------------------------------------------------------------ |
| HttpRequestEncoder   | 将HttpRequest HttpContent 和LastHttpContent 消息编码为字节   |
| HttpResponseEncoder  | 将HttpResponse HttpContent 和LastHtpContent 消息编码为字节   |
| HttpRequestDencoder  | 将字节解码为HttpRequest HttpContent 和LastHttpContent 消息   |
| HttpResponseDencoder | 将字节解码为 HttpResponse HttpContent 和LastHtpContent  消息 |

