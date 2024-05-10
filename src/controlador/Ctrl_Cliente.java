package controlador;

import java.sql.Statement;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Cliente;
import java.sql.ResultSet;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Ctrl_Cliente {

    /**
     * *******************************
     * MÉTODO PARA REGISTRAR CLIENTES.
     *
     * *******************************
     * @param objeto
     * @return
     */
    public boolean crear(Cliente objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT into tb_clientes VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
            consulta.setString(15, objeto.getTipo_precio());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al crear el cliente: " + e);
        }
        return respuesta;
    }

    /**
     * *******************************************
     * MÉTODO PARA COMPROBAR SI EXISTE UN CLIENTE.
     * *******************************************
     *
     * @param cliente
     * @param nif
     * @return
     */
    public boolean existeCliente(String cliente, String nif) {
        boolean respuesta = false;
        String sql = "SELECT nombre FROM tb_clientes where nombre = '" + cliente + "' OR nif = '" + nif + "'";
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
            System.out.println("Error al consultar el cliente: " + e);
        }
        return respuesta;
    }

    /**
     * **********************************
     * MÉTODO PARA ACTUALIZAR UN CLIENTE.
     *
     * **********************************
     *
     * @param objeto El cliente a actualizar.
     * @param idCliente El ID del cliente que se va a actualizar.
     * @return true si la actualización fue exitosa, false si falló.
     */
    public boolean actualizar(Cliente objeto, int idCliente) {
        boolean respuesta = false;
        Connection cn = null;
        PreparedStatement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.prepareStatement("UPDATE tb_clientes SET nombre = ?, nif = ?, email = ?, telefono = ?, movil = ?, direccion = ?, poblacion = ?, c_postal = ?, "
                    + "provincia = ?, pais = ?, n_comercial = ?, condiciones_pago = ?,  website = ?, tipo_precio = ? WHERE idCliente = '" + idCliente + "'");
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
            consulta.setString(14, objeto.getTipo_precio());

            int filasActualizadas = consulta.executeUpdate();
            if (filasActualizadas > 0) {
                respuesta = true;
            } else {
                System.out.println("No se actualizó ninguna fila.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e);
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
     * ********************************
     * MÉTODO PARA ELIMINAR UN CLIENTE.
     *
     * ********************************
     *
     * @param idCliente
     * @return
     */
    public boolean eliminar(int idCliente) {
        boolean respuesta = false;
        Connection cn = null;
        Statement consulta = null;
        try {
            cn = Conexion.conectar();
            consulta = cn.createStatement();
            String sql = "DELETE FROM tb_clientes WHERE idCliente = " + idCliente;

            int filasEliminadas = consulta.executeUpdate(sql);
            if (filasEliminadas > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e);
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
