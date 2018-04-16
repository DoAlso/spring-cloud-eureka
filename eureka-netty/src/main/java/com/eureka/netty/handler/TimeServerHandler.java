package com.eureka.netty.handler;

import com.eureka.common.utils.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"通道{}连接注册成功......",ctx.channel().metadata().toString());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"通道{}注销成功......",ctx.channel().metadata().toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"通道{}保持活跃......",ctx.channel().metadata().toString());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		LogUtil.info(logger,"通道{}触发了{}事件......",ctx.channel().metadata().toString(),evt.getClass().getSimpleName());
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"channelWritabilityChanged()");
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"handlerAdded()");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"handlerRemoved()");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"通道激活......");
		final ByteBuf time = ctx.alloc().buffer(4); // (2)
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        }); // (4)
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//    	ByteBuf byteBuf = (ByteBuf)msg;
//    	byte[] bytes = new byte[byteBuf.readableBytes()];
//    	byteBuf.readBytes(bytes);
		String body = new String((byte[])msg,"UTF-8");
		LogUtil.info(logger,"接收到的客户端数据是:{}",body);
		String currentTime = "QUERY TIME ORDER".equals(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		ctx.writeAndFlush(currentTime.getBytes());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		LogUtil.info(logger,"数据读取完成......");
		ctx.flush();
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
