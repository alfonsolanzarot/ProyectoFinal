package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase maneja la conexión a la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Conexion {

    /**
     * Constructor por defecto de la clase Conexion. Este constructor no realiza
     * ninguna acción específica.
     */
    public Conexion() {
        // Constructor por defecto
    }

    /**
     * Constructor por defecto para la clase Conexion.
     *
     * @return La conexión
     */
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
