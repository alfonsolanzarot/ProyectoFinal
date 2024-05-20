package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.FacturaCompra;

/**
 * Controlador para manejar las operaciones relacionadas con las fcturas de compra.
 * Permite registrar, verificar la existencia, obtener el próximo ID disponible
 * y actualizar facturas de compra en la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_FacturaCompra {

    /**
     * Constructor por defecto para la clase Ctrl_FacturaCompra.
     */
    public Ctrl_FacturaCompra() {
        // Constructor por defecto
    }

    /**
     * Método para registrar una nueva factura de compra en la base de datos.
     *
     * @param objeto La factura de compra a registrar.
     * @return true si se registra correctamente, false si falla.
     */
    public boolean crear(FacturaCompra objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_factura_compra values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, 0);
            consulta.setInt(2, objeto.getIdProveedor());
            consulta.setString(3, objeto.getFecha());
            consulta.setString(4, objeto.getNumero());
            consulta.setString(5, objeto.getNombre_proveedor());
            consulta.setString(6, objeto.getDescripcion());
            consulta.setDouble(7, objeto.getBase_imponible());
            consulta.setDouble(8, objeto.getTipo_iva());
            consulta.setDouble(9, objeto.getIva());
            consulta.setDouble(10, objeto.getBase_exenta());
            consulta.setDouble(11, objeto.getRetencion());
            consulta.setDouble(12, objeto.getTotal());
            consulta.setBoolean(13, objeto.isDomiciliado());
            consulta.setString(14, objeto.getObservaciones());
            consulta.setString(15, objeto.getAsociada());
            consulta.setString(16, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al crear la factura de compra: " + e);
        }
        return respuesta;
    }

    /**
     * Método para verificar si una factura de compra con el número dado ya
     * existe en la base de datos.
     *
     * @param numero El número de factura de compra a verificar.
     * @return true si la fatura de compra ya existe, false si no.
     */
    public boolean existeFacturaCompra(String numero) {
        boolean existe = false;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            cn = Conexion.conectar();
            st = cn.createStatement();
            String sql = "SELECT numero FROM tb_factura_compra WHERE numero = '" + numero + "'";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la base de datos: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e);
            }
        }
        return existe;
    }

    /**
     * Método para obtener el próximo ID disponible para una factura de compra.
     *
     * @return El próximo ID disponible.
     */
    public int obtenerProximoIdFacturaCompra() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int proximoId = 0;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT MAX(idFacturaCompra) + 1 AS proximoId FROM tb_factura_compra";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                proximoId = rs.getInt("proximoId");
                if (rs.wasNull()) {
                    proximoId = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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

        return proximoId;
    }

    /**
     * Método para crear/actualizar una factura de compra en la base de datos.
     *
     * @param facturaCompra La factura de compra a actualizar.
     * @return true si la actualización fue exitosa, false si falló.
     */
    public boolean actualizarFacturaCompra(FacturaCompra facturaCompra) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            String query = "UPDATE tb_factura_compra SET idProveedor=?, fecha=?, numero=?, nombre_proveedor=?, descripcion=?, base_imponible=?, tipo_iva=?,"
                    + "iva=?, base_exenta=?, retencion=?, total=?, domiciliado=?, observaciones=?, asociada=?, estado=? WHERE idFacturaCompra=?";
            PreparedStatement consulta = cn.prepareStatement(query);

            consulta.setDouble(1, facturaCompra.getIdProveedor());
            consulta.setString(2, facturaCompra.getFecha());
            consulta.setString(3, facturaCompra.getNumero());
            consulta.setString(4, facturaCompra.getNombre_proveedor());
            consulta.setString(5, facturaCompra.getDescripcion());
            consulta.setDouble(6, facturaCompra.getBase_imponible());
            consulta.setDouble(7, facturaCompra.getTipo_iva());
            consulta.setDouble(8, facturaCompra.getIva());
            consulta.setDouble(9, facturaCompra.getBase_exenta());
            consulta.setDouble(10, facturaCompra.getRetencion());
            consulta.setDouble(11, facturaCompra.getTotal());
            consulta.setBoolean(12, facturaCompra.isDomiciliado());
            consulta.setString(13, facturaCompra.getObservaciones());
            consulta.setString(14, facturaCompra.getAsociada());
            consulta.setString(15, facturaCompra.getEstado());
            consulta.setInt(16, facturaCompra.getIdFacturaCompra());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la factura de compra: " + e);
        }

        return respuesta;
    }

    /**
     * Método para eliminar una factura de compra de la base de datos.
     *
     * @param idFacturaCompra El ID de la factura de compra a eliminar.
     * @return true si la factura de compra fue eliminada exitosamente, false en caso
     * contrario.
     */
    public boolean eliminar(int idFacturaCompra) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_factura_compra WHERE idFacturaCompra = " + idFacturaCompra;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la factura de compra: " + e);
        } finally {
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
