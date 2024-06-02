package com.cibertec.xlsx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cibertec.bd.Imagen;

import util.MyConnection;

public class GenerateExcel {
    
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

                // Creando el archivo Excel mediante POI
                try (Workbook excel = new XSSFWorkbook()) {
                   
                   // Creando la hoja
                   int[] HEADER_WITH = { 8000, 3000, 20000  };

                   Sheet hoja = excel.createSheet("Imagenes");
                   for (int i = 0; i < HEADER_WITH.length; i++) {
                       hoja.setColumnWidth(i, HEADER_WITH[i]);
                   }

                   Font fuente = excel.createFont();
                   fuente.setFontHeightInPoints((short) 10);
                   fuente.setFontName("Arial");
                   fuente.setBold(true);
                   fuente.setColor(IndexedColors.WHITE.getIndex());

                   CellStyle estiloCeldaCentrado = excel.createCellStyle();
                   estiloCeldaCentrado.setWrapText(true);
                   estiloCeldaCentrado.setAlignment(HorizontalAlignment.CENTER);
                   estiloCeldaCentrado.setVerticalAlignment(VerticalAlignment.CENTER);
                   estiloCeldaCentrado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                   estiloCeldaCentrado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                   estiloCeldaCentrado.setFont(fuente);
                   
                   //creando la cabecera
                   hoja.createRow(0);
                   Cell celda0 = hoja.getRow(0).createCell(0);
                   celda0.setCellValue("Nombre");
                   celda0.setCellStyle(estiloCeldaCentrado);
                    
                   Cell celda1 = hoja.getRow(0).createCell(1);
                   celda1.setCellValue("Tamaño");
                   celda1.setCellStyle(estiloCeldaCentrado);

                   Cell celda2 = hoja.getRow(0).createCell(2);
                   celda2.setCellValue("Ruta");
                   celda2.setCellStyle(estiloCeldaCentrado);

                   // Creando la fila
                   int numFila = 1;
                   for (Imagen imagen : lista) {
                       // Creando la fila
                       hoja.createRow(numFila);
                       // Creando la celda
                       hoja.getRow(numFila).createCell(0).setCellValue(imagen.getNombre());
                       hoja.getRow(numFila).createCell(1).setCellValue(imagen.getTamano());
                       hoja.getRow(numFila).createCell(2).setCellValue(imagen.getRuta());
                       numFila++;
                   }
   
                  // Escribir el archivo
                  try (FileOutputStream file = new FileOutputStream("D:\\server\\imagen4.xlsx")) {
                         excel.write(file);
                         System.out.println("Successfully Copied Excel Object to File...");
                  } catch (IOException e) {
                         e.printStackTrace();
                  }
            } catch (IOException e) {
                e.printStackTrace();
            }
               

        }
    
}
