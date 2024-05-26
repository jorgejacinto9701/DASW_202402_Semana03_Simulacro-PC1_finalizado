package com.cibertec;

import java.io.IOException;
import java.net.Socket;

public class Client {
    
    private static final String HOST = "localhost";
    private static final int PORT = 13;
    
    public Client(){
        System.out.println("1 >> [ini] Client constructor");
        try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

            
            System.out.println("4 >> final for client...");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Client();
    }
    

}
