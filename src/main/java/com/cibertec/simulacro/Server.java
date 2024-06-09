package com.cibertec.simulacro;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
        private static final int PORT = 13;

    public Server(){
        System.out.println("1 >> [ini] Server constructor");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("2 >> waiting for client...");                
            while(true){
                Socket clienSocket = serverSocket.accept();
                System.out.println("3 >> accepted client connection...");
                
                //Generar nombre del archivo
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String rutaArchivo = "D:\\server\\G1_JACINTO_" + sdf.format(new Date()) + ".zip";
                File file = new File(rutaArchivo);
                System.out.println("Archivo creado: " + file.getAbsolutePath());

                //Flujo de datos de entrada y salida
                FileOutputStream fos = new FileOutputStream(file);
                DataInputStream entrada = new DataInputStream(clienSocket.getInputStream());

                //Recibiendo archivo
                byte[] buffer = new byte[1024];
                int count;
                while ((count = entrada.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                System.out.println("Archivo recibido: " + file.getAbsolutePath());

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
