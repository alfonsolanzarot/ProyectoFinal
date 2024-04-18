package controlador;

import java.sql.Statement;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Producto;
import java.sql.ResultSet;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Producto {

    /**
     * ********************************
     * MÉTODO PARA REGISTRAR PRODUCTOS.
     *
     * ********************************
     * @param objeto
     * @return
     */
    public boolean crear(Producto objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_productos values (?,?,?,?,?,?,?,?)");
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
            System.out.println("Error al crear el producto: " + e);
        }
        return respuesta;
    }

    /**
     * ********************************************
     * MÉTODO PARA COMPROBAR SI EXISTE UN PRODUCTO.
     * ********************************************
     *
     * @param producto
     * @param codigo_producto
     *
     * @return
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
            System.out.println("Error al consultar el producto: " + e);
        }
        return respuesta;
    }

    /**
     * ***********************************
     * MÉTODO PARA ACTUALIZAR UN PRODUCTO.
     *
     * ***********************************
     *
     * @param objeto El cliente a actualizar.
     * @param idProducto El ID del producto que se va a actualizar.
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
            System.out.println("Error al actualizar el producto: " + e);
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
     * *********************************
     * MÉTODO PARA ELIMINAR UN PRODUCTO.
     *
     * *********************************
     *
     * @param idProducto
     * @return
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
            System.out.println("Error al eliminar el producto: " + e);
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

}
