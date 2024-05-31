package com.cibertec.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

            File[] files = new File("D:\\client").listFiles();
            Random random = new Random();
            int index = random.nextInt(files.length);
            File file = files[index];
            System.out.println("Enviando archivo: " + file.getAbsolutePath());
           

            //Flujo de datos de entrada y salida para enviar el objeto
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

            //Arreglo de bytes del tamaño del archivo que se va a enviar
            FileInputStream fis = new FileInputStream(file);
            byte[] datos = new byte[(int) file.length()];
            fis.read(datos);
            
            //Se llena el objeto Imagen
            Imagen objImagen = new Imagen();
            objImagen.setNombre(file.getName());
            objImagen.setTamano((int) file.length());
            objImagen.setDatos(datos);
            objImagen.setRuta(file.getAbsolutePath());

            //Envio de objeto
            salida.writeObject(objImagen);

            fis.close();

            
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