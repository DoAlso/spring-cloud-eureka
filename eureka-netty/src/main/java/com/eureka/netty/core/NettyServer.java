package com.eureka.netty.core;

import com.eureka.common.utils.LogUtil;
import com.eureka.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    final boolean SSL = System.getProperty("ssl") != null;
    final int PORT = Integer.parseInt(System.getProperty("port", "9999"));

    public void bind() throws Exception{
        final SslContext sslContext;
        if(SSL){
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslContext = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        }else{
            sslContext = null;
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,1024)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    if(sslContext != null){
                        p.addLast(sslContext.newHandler(socketChannel.alloc()));
                    }
                    socketChannel.pipeline().addLast(new TimeServerHandler());
                }
            });
            //绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            LogUtil.info(logger,"NettyServer启动异常："+e.getMessage());
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
