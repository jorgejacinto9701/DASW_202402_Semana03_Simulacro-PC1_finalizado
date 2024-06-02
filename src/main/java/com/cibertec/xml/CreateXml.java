package com.cibertec.xml;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.cibertec.bd.Imagen;


import util.MyConnection;

public class CreateXml {
    
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
        
        // Creando el archivo XML con jackson   
        try {
            // Creando el archivo XML
            System.out.println("Creando archivo XML...");
            File file = new File("D:\\server\\imagen3.xml");

            XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            //generar el xml
            String xml = mapper.writeValueAsString(lista);
            System.out.println(xml);

            //generando file con jackson
            mapper.writeValue(file, lista);

            System.out.println("Archivo XML creado...");


        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    }
    
}
