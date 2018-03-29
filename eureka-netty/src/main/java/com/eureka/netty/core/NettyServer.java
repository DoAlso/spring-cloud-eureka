package com.eureka.netty.core;

import com.eureka.common.utils.LogUtil;
import com.eureka.netty.handler.TimeServerHandler;
import com.eureka.netty.properties.NettyServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private NettyServerProperties serverProperties;

    public void start(){
        LogUtil.info(logger,"NettyServer开始启动.....");
        bind(serverProperties.getPort());
        LogUtil.info(logger,"NettyServer启动完成，端口是：{}",serverProperties.getPort());
    }

    private void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,1024)
            .childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            //future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            LogUtil.info(logger,"NettyServer启动异常："+e.getMessage());
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        public void initChannel(SocketChannel socketChannel) throws Exception{
            socketChannel.pipeline().addLast(new TimeServerHandler());
//            for (String className:serverProperties.getClassName()){
//                Object object = Class.forName(className).newInstance();
//                if(object instanceof ChannelHandlerAdapter){
//                    socketChannel.pipeline().addLast((ChannelHandlerAdapter)object);
//                }
//            }
        }
    }
}
