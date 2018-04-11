package com.eureka.netty.core;

import com.eureka.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class TimeServer {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8006"));

    private void bind() throws Exception {
        final SslContext sslContext;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslContext = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslContext = null;
        }

        EventLoopGroup boss = new NioEventLoopGroup(50);
        EventLoopGroup worker = new NioEventLoopGroup(50);
        ServerBootstrap sbs = new ServerBootstrap();
        try {
            sbs.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //(4)
                    .option(ChannelOption.SO_BACKLOG, 1024)//(5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel arg0) throws Exception {
                            ChannelPipeline p = arg0.pipeline();
                            if (sslContext != null) {
                                p.addLast(sslContext.newHandler(arg0.alloc()));
                            }
//                            p.addLast(new ByteArrayDecoder());
//                            p.addLast(new ByteArrayEncoder());
							p.addLast(new StringEncoder());
							p.addLast(new StringDecoder());
                            p.addLast(new TimeServerHandler());
                        }
                    });

            ChannelFuture cf = sbs.bind(PORT).sync();
            cf.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("TimeServer Start On Port:" + PORT);
        new TimeServer().bind();
    }
}
