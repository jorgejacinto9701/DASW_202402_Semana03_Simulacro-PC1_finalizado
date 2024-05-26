package com.cibertec.archivos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;



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

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
                String fecha = sdf.format(new java.util.Date());
                String nombreArchivo = "D:/server/foto_" + fecha + ".png";    

                File file = new File(nombreArchivo);
                System.out.println("Receiving file: " + file.getName());

                //Flujos de entrada y salida
                FileOutputStream fos = new FileOutputStream(file);
                DataInputStream entrada = new DataInputStream(clienSocket.getInputStream());

                //Recibir el archivo del cliente
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = entrada.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
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
