package com.cibertec.mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

                //Flujos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
                PrintWriter salida = new PrintWriter(clienSocket.getOutputStream(), true);

                String tipoCliente = entrada.readLine();
                String costo = "";
                switch (tipoCliente) {
                    case "PLATEA":      costo   = "50"; break;
                    case "VIP":         costo   = "150"; break;
                    case "PLATINIUM":   costo   = "250"; break;
                }    
                salida.println(costo);

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
