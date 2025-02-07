package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConexion {
	public static Connection getConexion() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/restaurante_db?useSSL=false&useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String password = "mysql";
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Driver no instalado! " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error: No se pudo conectar a la BD! " + e.getMessage());
		}
		return con;
	}
}
