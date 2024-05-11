package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

/**
 * 
 * @author Alfonso Lanzarot
 */
public class ServicioUsuario {

    /**
     * ********************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN USUARIO REGISTRADO A LA TABLA
     * USUARIOS.
     * ********************************************************************
     * @param rs
     * @return 
     * @throws java.sql.SQLException
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
    }
}
