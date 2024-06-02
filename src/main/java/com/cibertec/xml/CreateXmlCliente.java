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
import com.json.Cliente;

import util.MyConnection;

public class CreateXmlCliente {
    
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

        try {
            // Creando el archivo XML
            System.out.println("Creando archivo XML...");
            File file = new File("D:\\server\\cliente.xml");

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


