package com.cibertec.archivos;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 13;
    
    public Client(){
        System.out.println("1 >> [ini] Client constructor");
        try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

            File[] files = {new File("D:/client/foto1.png"), 
                                new File("D:/client/foto2.png"),
                                new File("D:/client/foto3.png")};

                Random random = new Random();
                int postImg = random.nextInt(files.length);
                File file = files[postImg];
                System.out.println("Sending file: " + file.getName());

                //Flujos de entrada y salida
                FileInputStream fis = new FileInputStream(file);
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

                //Enviar el archivo al cliente
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    salida.write(buffer, 0, len);
                }

                fis.close();
                salida.close();
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
