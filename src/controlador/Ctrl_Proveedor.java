package controlador;

import java.sql.Statement;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Proveedor;
import java.sql.ResultSet;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Proveedor {

    /**
     * **********************************
     * MÉTODO PARA REGISTRAR PROVEEDORES.
     *
     * **********************************
     * @param objeto
     * @return
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
            System.out.println("Error al crear el proveedor: " + e);
        }
        return respuesta;
    }

    /**
     * *********************************************
     * MÉTODO PARA COMPROBAR SI EXISTE UN PROVEEDOR.
     * *********************************************
     *
     * @param proveedor
     * @param nif
     * @return
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
            System.out.println("Error al consultar el proveedor: " + e);
        }
        return respuesta;
    }

    /**
     * ************************************
     * MÉTODO PARA ACTUALIZAR UN PROVEEDOR.
     *
     * ************************************
     *
     * @param objeto El proveedor a actualizar.
     * @param idProveedor El ID del proveedor que se va a actualizar.
     * @return true si la actualización fue exitosa, false si falló.
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
                System.out.println("No se actualizó ninguna fila.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el proveedor: " + e);
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
     * **********************************
     * MÉTODO PARA ELIMINAR UN PROVEEDOR.
     *
     * **********************************
     *
     * @param idProveedor
     * @return
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
            System.out.println("Error al eliminar el proveedor: " + e);
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
