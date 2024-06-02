package com.json;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import util.MyConnection;

public class CreateJsonCliente {
    
    public static void main(String[] args) {
        Connection conn = MyConnection.getConexion();
        System.out.println("Conectado a la base de datos");


        // Preparando la sentencia SQL
        List<Cliente> lista = new ArrayList<Cliente>();

        try {
            PreparedStatement ps = conn.prepareStatement("select * from cliente");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setDni(rs.getString("dni"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                lista.add(cliente);
            }
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
           e.printStackTrace();
        }
        
        // Creando el archivo JSON
        //Con formato en el json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Sin formato en el json
        //Gson gson = new Gson();

        String json = gson.toJson(lista);
        System.out.println(json);

        //generando file con gson
        try (FileWriter file = new FileWriter("D:\\server\\cliente.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
