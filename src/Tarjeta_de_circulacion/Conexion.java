
package Tarjeta_de_circulacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/tarjeta_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // Por defecto en XAMPP el root no tiene contraseña (déjalo en blanco "")

    public static Connection conectar() {
        Connection con = null;
        try {
            // Cargar el driver de MySQL (opcional en versiones modernas, pero recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Realizar la conexión
            con = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            // System.out.println("Conexión exitosa a la base de datos"); // Opcional para depurar
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el Driver de MySQL.\n" + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión a la Base de Datos:\n" + e.getMessage());
        }
        return con;
    }
}
