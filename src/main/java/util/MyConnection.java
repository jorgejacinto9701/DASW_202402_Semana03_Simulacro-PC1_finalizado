package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    
		static{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//Metodo para crear conexiones
		public static Connection  getConexion(){
			Connection salida = null;
			try {
				salida = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/bd_ejercicio04?serverTimezone=America/Lima",
						"root",
						"mysql");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return salida;	
		}
}
