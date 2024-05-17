package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.ProductoProforma;

/**
 * Controlador para manejar las operaciones relacionadas con los productos de
 * las proformas. Permite registrar y eliminar productos asociados a una
 * proforma en la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_ProductosProforma {

    /**
     * Constructor por defecto para la clase Ctrl_ProductosProforma.
     */
    public Ctrl_ProductosProforma() {
        // Constructor por defecto
    }

    /**
     * Método para registrar un nuevo producto asociado a una proforma en la
     * base de datos.
     *
     * @param producto El producto a registrar.
     * @return true si se registra correctamente, false si falla.
     */
    public static boolean insertar(ProductoProforma producto) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.conectar();
            String sql = "INSERT INTO tb_productos_proforma (idProducto, idProforma, codigo_producto, descripcion, cantidad, precio_unitario, tipo_iva, importe_iva, subtotal) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, producto.getIdProducto());
            stmt.setInt(2, producto.getIdProforma());
            stmt.setString(3, producto.getCodigo_producto());
            stmt.setString(4, producto.getDescripcion());
            stmt.setDouble(5, producto.getCantidad());
            stmt.setDouble(6, producto.getPrecio_unitario());
            stmt.setDouble(7, producto.getTipo_iva());
            stmt.setDouble(8, producto.getImporte_iva());
            stmt.setDouble(9, producto.getSubtotal());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar la conexión y el statement
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para eliminar un producto asociado a una proforma de la base de
     * datos.
     *
     * @param idProducto El ID del producto a eliminar.
     * @return true si se elimina correctamente, false si falla.
     */
    public boolean eliminar(int idProducto) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_productos_proforma WHERE idProducto = " + idProducto;

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
