package com.eureka.netty.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimerClient {

    public static void main(String[] args){
        Socket socket = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try{
            socket = new Socket("127.0.0.1",8080);
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter.print("QUERY TIME ORDER");
            String resp = bufferedReader.readLine();
            System.out.println("Now is :"+resp);
        }catch (Exception e){

        }
    }
}
