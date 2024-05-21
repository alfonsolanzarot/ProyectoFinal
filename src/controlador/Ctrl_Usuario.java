package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import modelo.Usuario;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios en
 * la base de datos. Proporciona métodos para crear, comprobar existencia,
 * actualizar, eliminar y autenticar usuarios.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Usuario {

    /**
     * Constructor por defecto para la clase Ctrl_Usuario.
     */
    public Ctrl_Usuario() {
        // Constructor por defecto
    }

    /**
     * Método para registrar un nuevo usuario en la base de datos.
     *
     * @param objeto El objeto Usuario que contiene los datos del nuevo usuario.
     * @return true si el usuario fue creado exitosamente, false en caso
     * contrario.
     */
    public boolean crear(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_usuarios (idRoles, nombre, apellidos, email, clave, estado) VALUES (?,?,?,?,?,?)");
            consulta.setInt(1, objeto.getIdRoles());
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getApellidos());
            consulta.setString(4, objeto.getEmail());
            consulta.setString(5, objeto.getClave());
            consulta.setBoolean(6, objeto.isEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el usuario: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para comprobar si un usuario existe en la base de datos.
     *
     * @param usuario El email del usuario a comprobar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean existeUsuario(String usuario) {
        boolean respuesta = false;
        String sql = "SELECT nombre FROM tb_usuarios where email = '" + usuario + "'";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    respuesta = true;
                }

                st.close();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el usuarios: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para actualizar los datos de un usuario en la base de datos.
     *
     * @param usuario El objeto Usuario con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(Usuario usuario) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            String query = "UPDATE tb_usuarios SET idRoles=?, nombre=?, apellidos=?, email=?, clave=?, estado=? WHERE idUsuario=?";
            PreparedStatement consulta = cn.prepareStatement(query);

            consulta.setInt(1, usuario.getIdRoles());
            consulta.setString(2, usuario.getNombre());
            consulta.setString(3, usuario.getApellidos());
            consulta.setString(4, usuario.getEmail());
            consulta.setString(5, usuario.getClave());
            consulta.setBoolean(6, usuario.isEstado());
            consulta.setInt(7, usuario.getIdUsuario());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuarios: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }

        return respuesta;
    }

    /**
     * Método para eliminar un usuario de la base de datos.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @return true si el usuario fue eliminado exitosamente, false en caso
     * contrario.
     */
    public boolean eliminar(int idUsuario) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_usuarios WHERE idUsuario = " + idUsuario;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        } finally {
            if (consulta != null) {
                try {
                    consulta.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la consulta: " + e,
                            "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e,
                            "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        }
        return respuesta;
    }

    /**
     * Método para autenticar a un usuario en el sistema.
     *
     * @param objeto El objeto Usuario que contiene los datos de inicio de
     * sesión.
     * @return true si el inicio de sesión fue exitoso, false en caso contrario.
     */
    public boolean loginUser(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        String sql = "SELECT email, clave, idRoles, nombre, apellidos, estado FROM tb_usuarios WHERE email = ? AND clave = ?";
        PreparedStatement consulta;
        try {
            consulta = cn.prepareStatement(sql);
            consulta.setString(1, objeto.getEmail());
            consulta.setString(2, objeto.getClave());
            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                int estadoUsuario = rs.getInt("estado");
                if (estadoUsuario == 1) {
                    respuesta = true;
                    objeto.setIdRoles(rs.getInt("idRoles"));
                    objeto.setNombre(rs.getString("nombre"));
                    objeto.setApellidos(rs.getString("apellidos"));
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario está inactivo. Acceda con un usuario administrador y modifique su estado.",
                            "USUARIO INACTIVO", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctos.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.toString());
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        }
        return respuesta;
    }

    /**
     * Método para obtener un icono redimensionado de atención o advertencia.
     *
     * @param path Ruta del icono.
     * @param width Ancho del icono.
     * @param heigth Altura del icono.
     * @return El icono redimensionado.
     */
    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

}
