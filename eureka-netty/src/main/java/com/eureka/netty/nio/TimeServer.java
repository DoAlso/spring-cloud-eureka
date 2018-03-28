package com.eureka.netty.nio;

public class TimeServer {

    private static int port = 8080;

    public static void main(String[] args){
       if(args != null && args.length >0){
           port = Integer.valueOf(args[0]);
       }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-THREAD").start();
    }
}
