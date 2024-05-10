package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Proforma;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Proforma {

    /**
     * ********************************
     * MÉTODO PARA REGISTRAR PROFORMAS.
     *
     * ********************************
     * @param objeto
     * @return
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
            System.out.println("Error al crear la proforma: " + e);
        }
        return respuesta;
    }

    /**
     * *********************************************
     * MÉTODO PARA COMPROBAR SI EXISTE UNA PROFORMA.
     * *********************************************
     *
     * @param numero
     *
     * @return
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
                // Si hay al menos un resultado, significa que el número de proforma ya existe en la base de datos
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la base de datos: " + e);
        } finally {
            // Cerrar conexiones y recursos
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

    
    // Método para obtener el próximo ID disponible para una factura proforma
    public int obtenerProximoIdProforma() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int proximoId = 0;

        try {
            conn = Conexion.conectar();

            // Consulta SQL para obtener el próximo ID disponible
            String sql = "SELECT MAX(idProforma) + 1 AS proximoId FROM tb_proformas";

            // Preparar la declaración SQL
            stmt = conn.prepareStatement(sql);

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Verificar si se encontraron resultados
            if (rs.next()) {
                // Obtener el próximo ID disponible
                proximoId = rs.getInt("proximoId");
                if (rs.wasNull()) {
                    proximoId = 1; // Establecer el valor predeterminado si el resultado es NULL
                }

            }
        } catch (SQLException e) {
            // Manejar cualquier error de SQL
            e.printStackTrace();
        } finally {
            // Cerrar la conexión, el statement y el resultSet
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
     * ******************************************
     * MÉTODO PARA CREAR/ACTUALIZAR UNA PROFORMA.
     *
     * ******************************************
     *
     * @param proforma
     * @return true si la actualización fue exitosa, false si falló.
     */

    public boolean actualizarProforma(Proforma proforma) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            // Preparar la consulta SQL para actualizar la proforma
            String query = "UPDATE tb_proformas SET transporte=?, seguro=?, descuento=?, incoterm=?, observaciones=?, kilos=?, suma_subtotal=?, suma_iva=?, total=?, estado=? WHERE idProforma=?";
            PreparedStatement consulta = cn.prepareStatement(query);

            // Establecer los valores en la consulta SQL
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
            

            // Ejecutar la consulta y comprobar si se actualizó correctamente
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close(); // Cerrar la conexión después de realizar la consulta

        } catch (SQLException e) {
            System.out.println("Error al actualizar la proforma: " + e);
        }

        return respuesta;
    }

//    /**
//     * ************************************
//     * MÉTODO PARA ACTUALIZAR UNA PROFORMA.
//     *
//     * ************************************
//     *
//     * @param objeto El cliente a actualizar.
//     * @param idProforma El ID de la proforma que se va a actualizar.
//     * @return true si la actualización fue exitosa, false si falló.
//     */
//    public boolean actualizar(Proforma objeto, int idProforma) {
//        boolean respuesta = false;
//        Connection cn = null;
//        PreparedStatement consulta = null;
//        try {
//            cn = Conexion.conectar();
//            consulta = cn.prepareStatement("UPDATE tb_proformas SET fecha = ?, numero = ?, nombre_cliente = ?, nif = ?, condiciones_pago = ?, "
//                    + "validez = ?, direccion = ?, poblacion = ?, c_postal = ?, provincia = ?, incoterm = ?, pais = ?, transporte = ?,"
//                    + "seguro = ?, kilos = ?, suma_subtotal = ?, suma_iva = ?, descuento = ?, total = ?, observaciones = ?, tipo_precio = ?, estado =? WHERE idProforma = '" + idProforma + "'");
//            consulta.setDate(1, objeto.getFecha());
//            consulta.setString(2, objeto.getNumero());
//            consulta.setString(3, objeto.getNombre_cliente());
//            consulta.setString(4, objeto.getNif());
//            consulta.setString(5, objeto.getCondiciones_pago());
//            consulta.setDate(6, objeto.getFecha());
//            consulta.setString(7, objeto.getDireccion());
//            consulta.setString(8, objeto.getPoblacion());
//            consulta.setString(9, objeto.getC_postal());
//            consulta.setString(10, objeto.getProvincia());
//            consulta.setString(11, objeto.getIncoterm());
//            consulta.setString(12, objeto.getPais());
//            consulta.setDouble(13, objeto.getTransporte());
//            consulta.setDouble(14, objeto.getSeguro());
//            consulta.setDouble(15, objeto.getKilos());
//            consulta.setDouble(16, objeto.getSuma_subtotal());
//            consulta.setDouble(17, objeto.getSuma_iva());
//            consulta.setDouble(18, objeto.getDescuento());
//            consulta.setDouble(19, objeto.getTotal());
//            consulta.setString(20, objeto.getObservaciones());
//            consulta.setString(21, objeto.getEstado());
//            consulta.setString(22, objeto.getTipo_precio());
//
//            int filasActualizadas = consulta.executeUpdate();
//            if (filasActualizadas > 0) {
//                respuesta = true;
//            } else {
//                System.out.println("No se actualizó ninguna fila.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al actualizar la proforma: " + e);
//        } finally {
//            // Cerramos los recursos en un bloque finally para asegurarnos de que se cierren correctamente, independientemente de si hay una excepción o no.
//            if (consulta != null) {
//                try {
//                    consulta.close();
//                } catch (SQLException e) {
//                    System.out.println("Error al cerrar la consulta: " + e);
//                }
//            }
//            if (cn != null) {
//                try {
//                    cn.close();
//                } catch (SQLException e) {
//                    System.out.println("Error al cerrar la conexión: " + e);
//                }
//            }
//        }
//
//        return respuesta;
//    }

    
}
