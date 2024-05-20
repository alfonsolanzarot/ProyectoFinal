package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.VencimientoFacturaCompra;

/**
 * Controlador para manejar las operaciones relacionadas con los vencimientos de
 * las facturas de compra. Permite registrar y eliminar productos asociados a una
 * factura de compra en la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_VencimientosFacturaCompra {

    /**
     * Constructor por defecto para la clase Ctrl_VencimientosFacturaCompra.
     */
    public Ctrl_VencimientosFacturaCompra() {
        // Constructor por defecto
    }

    /**
     * Método para registrar un nuevo vencimiento asociado a una factura de compra en la
     * base de datos.
     *
     * @param vencimiento El vencimiento a registrar.
     * @return true si se registra correctamente, false si falla.
     */
    public static boolean insertar(VencimientoFacturaCompra vencimiento) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.conectar();
            String sql = "INSERT INTO tb_vencimientos_pago (idFacturaCompra, fecha_vencimiento, importe_pendiente, fecha_pago, importe_pagado, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vencimiento.getIdFacturaCompra());
            stmt.setString(2, vencimiento.getFecha_vencimiento());
            stmt.setDouble(3, vencimiento.getImporte_pendiente());
            stmt.setString(4, vencimiento.getFecha_pago());
            stmt.setDouble(5, vencimiento.getImporte_pagado());
            stmt.setBoolean(6, vencimiento.isEstado());
          
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
     * Método para eliminar un vencimiento asociado a una factura de compra de la base de
     * datos.
     *
     * @param idFacturaCompra El ID del producto a eliminar.
     * @return true si se elimina correctamente, false si falla.
     */
    public boolean eliminar(int idFacturaCompra) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_vencimientos_pago WHERE idFacturaCompra = " + idFacturaCompra;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el vencimiento: " + e);
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
