package com.cibertec.simulacro;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import util.MyConnection;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 13;

    public Client() {
          try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

            generateArchivos();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filename = "D:\\cliente\\G1_Jacinto_" + sdf.format(new Date()) + ".zip";
            File file = new File(filename);
            System.out.println("Enviando archivo: " + file.getAbsolutePath());

            //Flujo de datos de entrada y salida
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // Envio de archivo
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                salida.write(buffer, 0, count);
            }
            fis.close();
            
            System.out.println("Archivo enviado: " + file.getAbsolutePath());
            System.out.println("4 >> final for client...");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
        new Client();
    }

    public void generateArchivos() {
        System.out.println("Generando archivos...");

        //PASO 1 : acceder a la base de datos
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
                cliente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                lista.add(cliente);
            }
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // PASO 2 : generar el archivo JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(lista);

        //generando file con gson
        try (FileWriter file = new FileWriter("D:\\cliente\\cliente.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // PASO 3 : generar el archivo XML
        try {
            // Creando el archivo XML
            System.out.println("Creando archivo XML...");
            File file = new File("D:\\cliente\\cliente.xml");

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

        // PASO 4 : generar el archivo ZIP
        try{
            //Se crea un archivo zip
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filename = "D:\\cliente\\G1_Jacinto_" + sdf.format(new Date()) + ".zip";
            
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(filename));

            //XML
            File file1 = new File("D:\\cliente\\cliente.xml");
            FileInputStream fis1 = new FileInputStream(file1);
            ZipEntry zipEntry1 = new ZipEntry(file1.getName());
            zos.putNextEntry(zipEntry1);

            byte[] datos1 = new byte[(int) file1.length()];
            fis1.read(datos1);
            zos.write(datos1);

            fis1.close();
            zos.closeEntry();

            // JSON
            File file2 = new File("D:\\cliente\\cliente.json");
            FileInputStream fis2 = new FileInputStream(file2);
            ZipEntry zipEntry2 = new ZipEntry(file2.getName());
            zos.putNextEntry(zipEntry2);

            byte[] datos2 = new byte[(int) file2.length()];
            fis2.read(datos2);
            zos.write(datos2);

            fis2.close();
            zos.closeEntry();

            //Se cierra el archivo zip
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}
