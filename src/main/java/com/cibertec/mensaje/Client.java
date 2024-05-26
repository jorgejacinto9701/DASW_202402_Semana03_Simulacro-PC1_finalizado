package com.cibertec.mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

            //Flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            String[] tipos = {"PLATEA", "VIP", "PLATINIUM"};
            Random random = new Random();
            int tipoCliente =  random.nextInt(3); //valores entre 0 y 2
            String tipo = tipos[tipoCliente];
            System.out.println("tipo: " + tipo);
            
            //Enviar el tipo de cliente al servidor
            salida.println(tipo);

            //Recibir el costo del servidor
            String costo = entrada.readLine();
            System.out.println("costo: " + costo);

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
