package com.eureka.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandler implements Runnable {
    private String ip;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandler(String ip,int port){
        this.ip = ip == null ? "127.0.0.1":ip;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                if(iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    handlerInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException{
      if(key.isValid()){
          SocketChannel sc = (SocketChannel) key.channel();
          if(key.isConnectable()) {
              if (sc.finishConnect()) {
                  sc.register(selector, SelectionKey.OP_READ);
                  doWrite(sc);
              } else {
                  System.exit(1);
              }
          }
          if(key.isReadable()){
              ByteBuffer readBuffer = ByteBuffer.allocate(1024);
              int readBytes = sc.read(readBuffer);
              if(readBytes > 0){
                  readBuffer.flip();
                  byte[] bytes = new byte[readBuffer.remaining()];
                  String body = new String(bytes,"UTF-8");
                  System.out.println("Now is :"+body);
                  this.stop = true;
              }else if(readBytes < 0){
                  key.cancel();
                  sc.close();
              }else {

              }
          }
      }
    }

    private void doConnect() throws IOException {
        if(socketChannel.connect(new InetSocketAddress(ip,port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] bytes = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(writeBuffer.hasRemaining()){
            System.out.println("dsfcdsafdasga");
        }
    }
}
