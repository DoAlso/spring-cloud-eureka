package com.eureka.netty.bio;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerHandler implements Runnable {
    private PrintWriter printWriter;
    private Socket socket;
    private Scanner scanner;
    public ChatServerHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                printWriter.write(scanner.nextLine());
            }
            printWriter.flush();
        }catch (IOException e){
            if(printWriter != null){
                printWriter.close();
            }
            if(scanner !=null ){
                scanner.close();
            }
            if(socket != null){
                try {
                    socket.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }

            }
        }

    }
}
