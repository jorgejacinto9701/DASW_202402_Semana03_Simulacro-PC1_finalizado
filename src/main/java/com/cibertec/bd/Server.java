package com.cibertec.bd;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;

import util.MyConnection;

public class Server {
      private static final int PORT = 13;

    public Server(){
        System.out.println("1 >> [ini] Server constructor");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("2 >> waiting for client...");                
            while(true){
                Socket clienSocket = serverSocket.accept();
                System.out.println("3 >> accepted client connection...");
               
                //Flujo de datos de entrada y salida
                ObjectInputStream entrada = new ObjectInputStream(clienSocket.getInputStream());

                //Reglas de negocio
                Imagen  objImagen = (Imagen)entrada.readObject();
                System.out.println("Imagen recibida: " + objImagen.getNombre());
                System.out.println("Tamaño: " + objImagen.getTamano());
                
                //Guardar archivo en BD
                System.out.println("Guardando archivo en BD...");
                Connection conn = MyConnection.getConexion();
                PreparedStatement pstm = conn.prepareStatement("INSERT INTO archivo(nombre, tamano, datos, ruta) VALUES(?,?,?,?)");
                pstm.setString(1, objImagen.getNombre());
                pstm.setInt(2, objImagen.getTamano());
                pstm.setBytes(3, objImagen.getDatos());
                pstm.setString(4, objImagen.getRuta());
                int insertados = pstm.executeUpdate();
                System.out.println("Registros insertados: " + insertados);

                System.out.println("4 >> final for client...");
                clienSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new Server();
    }
}
