package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Producto;

/**
 * Controlador para gestionar las operaciones relacionadas con los productos.
 * Permite registrar, actualizar y eliminar productos en la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Producto {

    /**
     * Constructor por defecto para la clase Ctrl_Producto.
     */
    public Ctrl_Producto() {
        // Constructor por defecto
    }

    /**
     * Método para registrar un nuevo producto en la base de datos.
     *
     * @param objeto El producto a registrar.
     * @return true si se registra correctamente, false si falla.
     */
    public boolean crear(Producto objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_productos VALUES (?,?,?,?,?,?,?,?)");
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getCodigo());
            consulta.setString(3, objeto.getDescripcion());
            consulta.setString(4, objeto.getFormato());
            consulta.setDouble(5, objeto.getPesoUnitario());
            consulta.setDouble(6, objeto.getPrecioAlto());
            consulta.setDouble(7, objeto.getPrecioBajo());
            consulta.setDouble(8, objeto.getPrecioServicio());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el producto: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para comprobar si existe un producto o servicio en la base de
     * datos.
     *
     * @param producto La descripción del producto o servicio.
     * @param codigo_producto El código del producto o servicio.
     * @return true si el producto o servicio existe, false si no.
     */
    public boolean existeProducto(String producto, String codigo_producto) {
        boolean respuesta = false;
        String sql = "SELECT descripcion FROM tb_productos where descripcion = '" + producto + "' OR codigo_producto = '" + codigo_producto + "'";
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
            JOptionPane.showMessageDialog(null, "Error al consultar el producto: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para actualizar un producto o servicio en la base de datos.
     *
     * @param objeto El producto o servicio a actualizar.
     * @param idProducto El ID del producto o servicio que se va a actualizar.
     * @return true si la actualización fue exitosa, false si falló.
     */
    public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false;
        Connection cn = null;
        PreparedStatement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.prepareStatement("UPDATE tb_productos SET codigo_producto = ?, descripcion = ?, formato = ?, peso_unitario = ?, precio_alto = ?, "
                    + "precio_bajo = ?, precio_servicio = ? WHERE idProducto = '" + idProducto + "'");
            consulta.setString(1, objeto.getCodigo());
            consulta.setString(2, objeto.getDescripcion());
            consulta.setString(3, objeto.getFormato());
            consulta.setDouble(4, objeto.getPesoUnitario());
            consulta.setDouble(5, objeto.getPrecioAlto());
            consulta.setDouble(6, objeto.getPrecioBajo());
            consulta.setDouble(7, objeto.getPrecioServicio());

            int filasActualizadas = consulta.executeUpdate();
            if (filasActualizadas > 0) {
                respuesta = true;
            } else {
                System.out.println("No se actualizó ninguna fila.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        } finally {
            // Cerramos los recursos en un bloque finally para asegurarnos de que se cierren correctamente, independientemente de si hay una excepción o no.
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
     * Método para eliminar un producto o servicio de la base de datos.
     *
     * @param idProducto El ID del producto o servicio a eliminar.
     * @return true si se elimina correctamente, false si falla.
     */
    public boolean eliminar(int idProducto) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_productos WHERE idProducto = " + idProducto;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        } finally {
            // Cerramos los recursos en un bloque finally para asegurarnos de que se cierren correctamente, independientemente de si hay una excepción o no.
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
    } // Cierre del método.

}
