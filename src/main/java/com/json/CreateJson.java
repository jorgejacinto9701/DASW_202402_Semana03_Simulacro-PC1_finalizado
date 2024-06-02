package com.json;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cibertec.bd.Imagen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import util.MyConnection;

public class CreateJson {
    
    public static void main(String[] args) {
        Connection conn = MyConnection.getConexion();
        System.out.println("Conectado a la base de datos");


        // Preparando la sentencia SQL
        List<Imagen> lista = new ArrayList<Imagen>();

        try {
            PreparedStatement ps = conn.prepareStatement("select * from archivo");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Imagen imagen = new Imagen();
                imagen.setNombre(rs.getString("nombre"));
                imagen.setTamano(rs.getInt("tamano"));
                imagen.setRuta(rs.getString("ruta"));
                lista.add(imagen);
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
        try (FileWriter file = new FileWriter("D:\\server\\imagen.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
