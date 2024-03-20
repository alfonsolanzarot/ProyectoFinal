package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import modelo.Usuario;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Usuario {

    //MÉTODO PARA INICIAR SESIÓN
    public boolean loginUser(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        String sql = "select email, clave from tb_usuarios where email = '" + objeto.getEmail() + "' and clave = '" + objeto.getClave() + "'";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión");
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión");
        }
        return respuesta;
    }
}
