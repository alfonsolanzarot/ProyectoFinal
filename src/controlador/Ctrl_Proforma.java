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
import modelo.Proforma;

/**
 * Controlador para manejar las operaciones relacionadas con las proformas.
 * Permite registrar, verificar la existencia, obtener el próximo ID disponible
 * y actualizar proformas en la base de datos.
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Proforma {

    /**
     * Constructor por defecto para la clase Ctrl_Proforma.
     */
    public Ctrl_Proforma() {
        // Constructor por defecto
    }

    /**
     * Método para registrar una nueva proforma en la base de datos.
     *
     * @param objeto La proforma a registrar.
     * @return true si se registra correctamente, false si falla.
     */
    public boolean crear(Proforma objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_proformas values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, 0);
            consulta.setInt(2, objeto.getIdCliente());
            consulta.setDate(3, objeto.getFecha());
            consulta.setString(4, objeto.getNumero());
            consulta.setString(5, objeto.getNombre_cliente());
            consulta.setString(6, objeto.getNif());
            consulta.setString(7, objeto.getCondiciones_pago());
            consulta.setDate(8, objeto.getValidez());
            consulta.setString(9, objeto.getDireccion());
            consulta.setString(10, objeto.getPoblacion());
            consulta.setString(11, objeto.getC_postal());
            consulta.setString(12, objeto.getProvincia());
            consulta.setString(13, objeto.getIncoterm());
            consulta.setString(14, objeto.getPais());
            consulta.setDouble(15, objeto.getTransporte());
            consulta.setDouble(16, objeto.getSeguro());
            consulta.setDouble(17, objeto.getKilos());
            consulta.setDouble(18, objeto.getSuma_subtotal());
            consulta.setDouble(19, objeto.getSuma_iva());
            consulta.setDouble(20, objeto.getDescuento());
            consulta.setDouble(21, objeto.getTotal());
            consulta.setString(22, objeto.getObservaciones());
            consulta.setString(23, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la proforma: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
        return respuesta;
    }

    /**
     * Método para verificar si una proforma con el número dado ya existe en la
     * base de datos.
     *
     * @param numero El número de proforma a verificar.
     * @return true si la proforma ya existe, false si no.
     */
    public boolean existeProforma(String numero) {
        boolean existe = false;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            cn = Conexion.conectar();
            st = cn.createStatement();
            String sql = "SELECT numero FROM tb_proformas WHERE numero = '" + numero + "'";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar la base de datos: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
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
                JOptionPane.showMessageDialog(null, "Error al cerrar las conexiones: " + e,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        }
        return existe;
    }

    /**
     * Método para obtener el próximo ID disponible para una factura proforma.
     *
     * @return El próximo ID disponible.
     */
    public int obtenerProximoIdProforma() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int proximoId = 0;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT MAX(idProforma) + 1 AS proximoId FROM tb_proformas";
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
            JOptionPane.showMessageDialog(null, "Error al obtener el próximo ID de la proforma: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
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
                JOptionPane.showMessageDialog(null, "Error al cerrar las conexiones: " + e,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        }

        return proximoId;
    }

    /**
     * Método para crear/actualizar una proforma en la base de datos.
     *
     * @param proforma La proforma a actualizar.
     * @return true si la actualización fue exitosa, false si falló.
     */
    public boolean actualizarProforma(Proforma proforma) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            String query = "UPDATE tb_proformas SET transporte=?, seguro=?, descuento=?, incoterm=?, observaciones=?, kilos=?, suma_subtotal=?, suma_iva=?, total=?, estado=? WHERE idProforma=?";
            PreparedStatement consulta = cn.prepareStatement(query);

            consulta.setDouble(1, proforma.getTransporte());
            consulta.setDouble(2, proforma.getSeguro());
            consulta.setDouble(3, proforma.getDescuento());
            consulta.setString(4, proforma.getIncoterm());
            consulta.setString(5, proforma.getObservaciones());
            consulta.setDouble(6, proforma.getKilos());
            consulta.setDouble(7, proforma.getSuma_subtotal());
            consulta.setDouble(8, proforma.getSuma_iva());
            consulta.setDouble(9, proforma.getTotal());
            consulta.setString(10, proforma.getEstado());
            consulta.setInt(11, proforma.getIdProforma());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la proforma: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
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
