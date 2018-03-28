package com.eureka.netty.nio;


public class TimeClient {
    private static int port = 8080;
    public static void main(String[] args){
        if(args!=null&&args.length>0){
            port = Integer.valueOf(args[0]);
        }
        TimeClientHandler timeClientHandler = new TimeClientHandler(null,port);
        new Thread(timeClientHandler,"TIME-CLIENT-001").start();
    }
}
