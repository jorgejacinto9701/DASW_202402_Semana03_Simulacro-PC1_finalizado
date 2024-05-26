package com.cibertec;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 13;

    public Server(){
        System.out.println("1 >> [ini] Server constructor");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("2 >> waiting for client...");
                Socket clienSocket =   serverSocket.accept();
                System.out.println("3 >> accepted client connection...");

                System.out.println("4 >> final for client...");
                clienSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Server();
    }
}
