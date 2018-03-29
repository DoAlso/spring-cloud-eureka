package com.eureka.netty.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

//    public static void main(String[] args){
//        int port = 8080;
//        if(args != null && args.length > 0 ){
//            try {
//                port = Integer.valueOf(args[0]);
//            }catch (NumberFormatException e){
//
//            }
//        }
//        ServerSocket serverSocket = null;
//        try{
//            System.out.println("ChatServer start at port:"+port);
//            serverSocket = new ServerSocket(port);
//            while(true){
//                Socket socket = serverSocket.accept();
//                System.out.println("接收到连接的客户端："+socket.getRemoteSocketAddress());
//                //此处新开一个线程处理客户端Socket
//                new Thread(new ChatServerHandler(socket),Thread.currentThread().getName()).start();
//            }
//        }catch (IOException e){
//
//        }
//    }
}
