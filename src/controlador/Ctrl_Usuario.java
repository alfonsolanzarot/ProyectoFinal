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
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Usuario {

    /**
     * *******************************
     * MÉTODO PARA REGISTRAR USUARIOS.
     *
     * *******************************
     * @param objeto
     * @return
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
            System.out.println("Error al crear el usuario: " + e);
        }
        return respuesta;
    }

    /**
     * *******************************************
     * MÉTODO PARA COMPROBAR SI EXISTE UN USUARIO.
     * *******************************************
     *
     * @param usuario
     * @return
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
            System.out.println("Error al consultar el usuario: " + e);
        }
        return respuesta;
    }

    /**
     * **********************************
     * MÉTODO PARA ACTUALIZAR UN USUARIO.
     *
     * **********************************
     *
     * @param usuario
     * @return true si la actualización fue exitosa, false si falló.
     */
    public boolean actualizar(Usuario usuario) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            // Preparar la consulta SQL para actualizar el usuario
            String query = "UPDATE tb_usuarios SET idRoles=?, nombre=?, apellidos=?, email=?, clave=?, estado=? WHERE idUsuario=?";
            PreparedStatement consulta = cn.prepareStatement(query);

            // Establecer los valores en la consulta SQL
            consulta.setInt(1, usuario.getIdRoles());
            consulta.setString(2, usuario.getNombre());
            consulta.setString(3, usuario.getApellidos());
            consulta.setString(4, usuario.getEmail());
            consulta.setString(5, usuario.getClave());
            consulta.setBoolean(6, usuario.isEstado());
            consulta.setInt(7, usuario.getIdUsuario());

            // Ejecutar la consulta y comprobar si se actualizó correctamente
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close(); // Cerrar la conexión después de realizar la consulta

        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e);
        }

        return respuesta;
    }

    /**
     * ********************************
     * MÉTODO PARA ELIMINAR UN USUARIO.
     *
     * ********************************
     *
     * @param idUsuario
     * @return
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
            System.out.println("Error al eliminar el usuario: " + e);
        } finally {
            // Cerramos los recursos en un bloque finally para asegurarnos de que se cierren correctamente, independientemente de si hay una excepción o no.
            if (consulta != null) {
                try {
                    consulta.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la consulta: " + e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e);
                }
            }
        }
        return respuesta;
    }

    /**
     * ***************************
     * MÉTODO PARA INICIAR SESIÓN
     *
     * ***************************
     * @param objeto
     * @return
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
                    // El usuario está activo, se permite el inicio de sesión
                    respuesta = true;
                    objeto.setIdRoles(rs.getInt("idRoles"));
                    objeto.setNombre(rs.getString("nombre"));
                    objeto.setApellidos(rs.getString("apellidos"));
                } else {
                    // El usuario está inactivo, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "El usuario está inactivo. Acceda con un usuario administrador y modifique su estado.",
                            "USUARIO INACTIVO", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
                }
            } else {
                // Usuario o contraseña incorrectos
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctos.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.toString());
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión");
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.toString());
            }
        }
        return respuesta;
    }

    /**
     * *********************************************
     * MÉTODO DE ICONOS DE ATENCIÓN Y/O ADVERTENCIA.
     *
     * *********************************************
     *
     * @param path
     * @param width
     * @param heigth
     * @return
     */
    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

}
