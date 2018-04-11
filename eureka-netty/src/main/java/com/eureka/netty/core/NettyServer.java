package com.eureka.netty.core;

import com.eureka.common.utils.LogUtil;
import com.eureka.netty.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    /**
     * 创建bootstrap
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * Worker
     */
    EventLoopGroup workGroup = new NioEventLoopGroup();
    @Autowired
    private NettyProperties properties;

    @PostConstruct
    public void start() throws Exception{
        int port = properties.getPort();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO)) //(4)
                .option(ChannelOption.SO_BACKLOG, 1024)//(5)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        new Thread(()->{
            try{
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ByteArrayDecoder());
                        pipeline.addLast(new ByteArrayEncoder());
//							p.addLast(new StringEncoder());
//							p.addLast(new StringDecoder());
                        pipeline.addLast(new TimeServerHandler());
                    }
                });
                //绑定端口，同步等待成功
                ChannelFuture future = serverBootstrap.bind(port).sync();
                LogUtil.info(logger,"NettyServer Start On Port:{}",port);
                //等待服务端监听端口关闭
                future.channel().closeFuture().sync();
            }catch (InterruptedException e){
                LogUtil.error(logger,"NettyServer启动异常："+e.getMessage());
            }
        }).start();
    }


    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        LogUtil.info(logger,"关闭服务器....");
        //优雅退出
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

}
