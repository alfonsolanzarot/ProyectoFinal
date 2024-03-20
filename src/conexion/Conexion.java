package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Conexion {

    //Conexión local
    public static Connection conectar() {
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/bd_boms", "root", "dugu&7Photh&");
            return cn;
        } catch (SQLException e) {
            System.out.println("Error en la conexión local " + e);
        }
        return null;
    }
}
