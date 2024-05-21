package controlador;

import java.sql.Statement;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Proveedor;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Controlador para gestionar las operaciones relacionadas con los proveedores
 * en la base de datos. Proporciona métodos para crear, comprobar existencia,
 * actualizar y eliminar proveedores.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Proveedor {

    /**
     * Constructor por defecto para la clase Ctrl_Proveedor.
     */
    public Ctrl_Proveedor() {
        // Constructor por defecto
    }

    /**
     * Método para registrar un nuevo proveedor en la base de datos.
     *
     * @param objeto El objeto Proveedor que contiene los datos del nuevo
     * proveedor.
     * @return true si el proveedor fue creado exitosamente, false en caso
     * contrario.
     */
    public boolean crear(Proveedor objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_proveedores VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getNif());
            consulta.setString(4, objeto.getDireccion());
            consulta.setString(5, objeto.getPoblacion());
            consulta.setString(6, objeto.getC_postal());
            consulta.setString(7, objeto.getProvincia());
            consulta.setString(8, objeto.getPais());
            consulta.setString(9, objeto.getN_comercial());
            consulta.setString(10, objeto.getCondiciones_pago());
            consulta.setString(11, objeto.getEmail());
            consulta.setString(12, objeto.getTelefono());
            consulta.setString(13, objeto.getMovil());
            consulta.setString(14, objeto.getWebsite());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el proveedor: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para comprobar si un proveedor existe en la base de datos.
     *
     * @param proveedor El nombre del proveedor a comprobar.
     * @param nif El NIF del proveedor a comprobar.
     * @return true si el proveedor existe, false en caso contrario.
     */
    public boolean existeProveedor(String proveedor, String nif) {
        boolean respuesta = false;
        String sql = "SELECT nombre FROM tb_proveedores where nombre = '" + proveedor + "' OR nif = '" + nif + "'";
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
            JOptionPane.showMessageDialog(null, "Error al consultar el proveedor: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para actualizar los datos de un proveedor en la base de datos.
     *
     * @param objeto El objeto Proveedor con los datos actualizados.
     * @param idProveedor El ID del proveedor que se va a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(Proveedor objeto, int idProveedor) {
        boolean respuesta = false;
        Connection cn = null;
        PreparedStatement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.prepareStatement("UPDATE tb_proveedores SET nombre = ?, nif = ?, email = ?, telefono = ?, movil = ?, direccion = ?, poblacion = ?, c_postal = ?, "
                    + "provincia = ?, pais = ?, n_comercial = ?, condiciones_pago = ?,  website = ? WHERE idProveedor = '" + idProveedor + "'");
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getNif());
            consulta.setString(3, objeto.getEmail());
            consulta.setString(4, objeto.getTelefono());
            consulta.setString(5, objeto.getMovil());
            consulta.setString(6, objeto.getDireccion());
            consulta.setString(7, objeto.getPoblacion());
            consulta.setString(8, objeto.getC_postal());
            consulta.setString(9, objeto.getProvincia());
            consulta.setString(10, objeto.getPais());
            consulta.setString(11, objeto.getN_comercial());
            consulta.setString(12, objeto.getCondiciones_pago());
            consulta.setString(13, objeto.getWebsite());

            int filasActualizadas = consulta.executeUpdate();
            if (filasActualizadas > 0) {
                respuesta = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se actualizó ninguna fila",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + e,
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
     * Método para eliminar un proveedor de la base de datos.
     *
     * @param idProveedor El ID del proveedor a eliminar.
     * @return true si el proveedor fue eliminado exitosamente, false en caso
     * contrario.
     */
    public boolean eliminar(int idProveedor) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_proveedores WHERE idProveedor = " + idProveedor;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor: " + e,
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
