package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Cliente;

/**
 * Clase que proporciona servicios relacionados con los clientes registrados en
 * la tabla 'clientes'. Esta clase incluye métodos para asignar los datos de un
 * cliente desde un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioCliente {

    /**
     * Método para asignar los datos de un cliente registrado a partir de un
     * ResultSet.
     *
     * @param rs ResultSet que contiene los datos del cliente.
     * @return Cliente con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static Cliente asignarDatosCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getInt(1));
        cliente.setNombre(rs.getString(2));
        cliente.setNif(rs.getString(3));
        cliente.setEmail(rs.getString(4));
        cliente.setDireccion(rs.getString(5));
        cliente.setTelefono(rs.getString(6));
        cliente.setMovil(rs.getString(7));
        cliente.setPoblacion(rs.getString(8));
        cliente.setC_postal(rs.getString(9));
        cliente.setWebsite(rs.getString(10));
        cliente.setProvincia(rs.getString(11));
        cliente.setPais(rs.getString(12));
        cliente.setN_comercial(rs.getString(13));
        cliente.setCondiciones_pago(rs.getString(14));
        cliente.setTipo_precio(rs.getString(15));

        return cliente;
    } // Cierre del método.

} // Cierre de la clase.

