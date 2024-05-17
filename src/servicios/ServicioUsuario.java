package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

/**
 * Clase que proporciona servicios relacionados con los usuarios registrados en
 * la tabla de usuarios. Esta clase incluye un método para asignar los datos de
 * un usuario registrado en la tabla de usuarios desde un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioUsuario {

    /**
     * Método para asignar los datos de un usuario registrado en la tabla de
     * usuarios desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos del usuario.
     * @return Usuario con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static Usuario asignarDatosUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(rs.getInt(1));
        usuario.setIdRoles(rs.getInt(2));
        usuario.setNombre(rs.getString(3));
        usuario.setApellidos(rs.getString(4));
        usuario.setEmail(rs.getString(5));
        usuario.setClave(rs.getString(6));
        usuario.setEstado(rs.getBoolean(7));

        return usuario;
    } // Cierre del método.
} // Cierre de la clase.

