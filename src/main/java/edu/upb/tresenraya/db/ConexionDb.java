package edu.upb.tresenraya.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDb {

    private static final String DB_URL = "jdbc:sqlite:jugadores10.db";
    private static ConexionDb db;
    private Connection connection;

    static {
        db = new ConexionDb();
         try {
           
            db.createTable(); // Ensure the table exists when the connection is created
            System.out.println("Conexion a la base de datos establecida.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        System.out.println("Iniciando Singleton...");
    }

    // Constructor privado para Singleton
    private ConexionDb() {
       
        try {
            connection = DriverManager.getConnection(DB_URL);
          
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

     private void createTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS jugadores ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " nombre TEXT NOT NULL,"
                    + " ip TEXT NOT NULL"
                    + ");";
            stmt.execute(sql);
            System.out.println("Tabla 'jugadores' creada exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
            throw e;
        }
    }


    // Método para obtener la única instancia
    public static ConexionDb intance() {
        return db;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return connection;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexion cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
