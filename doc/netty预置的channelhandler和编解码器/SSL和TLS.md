netty 通过SslHandler 的channelhandler 实现SSL/TLS  ，其中SslHandler  内部使用SSLEngine 来完成实际的工作

### netty 的openSSL 和SSLEngine 的实现

netty 使用openSSL 工具包和 JDK 提供的SSLEngine 两种方式实现。其中， openSSL 的性能比JDK 的更好

如果OpenSSL 库可用，可以将Netty 程序配置为默认使用OpenSslEngine ，如果不可用，netty 将会使用JDK 来实现

 