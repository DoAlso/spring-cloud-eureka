package com.eureka.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


/**
 * 多路复用器会不断的轮询注册到其上的通道，如果某个通道
 * 发生了读写数据的行为则证明，该通道为就绪状态
 */
public class MultiplexerTimeServer implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;//多路复用器
    private volatile boolean stop;

    public MultiplexerTimeServer(int port){
        try {
            //初始化多路复用器
            selector = Selector.open();
            //初始化ServerSocketChannel通道
            serverSocketChannel = ServerSocketChannel.open();
            //设置通道为非阻塞
            serverSocketChannel.configureBlocking(false);
            //将ServerSocketChannel的socket绑定到指定端口上，此处默认为主机IP,也可以通过InetSocketAddress指定IP
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //将通道注册到多路复用器上，进行监听
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : "+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.stop = true;
    }

    /**
     * 在run方法中轮询所有注册的通道
     */
    @Override
    public void run() {
        while (!stop){
            try {
                //设置轮询超时时间
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try{
                        handlerInput(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_ACCEPT);
            }
            if(key.isReadable()){
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.write(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("The time server receive order :"+body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    doWirte(socketChannel,currentTime);
                }else if(readBytes < 0){
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWirte(SocketChannel socketChannel,String response) throws IOException {
        if(response != null && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
