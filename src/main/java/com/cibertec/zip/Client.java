package com.cibertec.zip;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:/client/fotos.zip"));

            for (File file : files) {
                 FileInputStream fis = new FileInputStream(file);
                 ZipEntry entry = new ZipEntry(file.getName());
                 zos.putNextEntry(entry);   

                 int len = 0;
                 byte[] buffer = new byte[1024];
                 
                 while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                 }
                 fis.close();
                 zos.closeEntry();
            }
            zos.close();

            File fileZip = new File("D:/client/fotos.zip");
            
            //Flujos de entrada y salida
            FileInputStream fis = new FileInputStream(fileZip);
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
