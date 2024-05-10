package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Cliente;

/**
 * 
 * @author Alfonso Lanzarot
 */
public class ServicioCliente {
    
    /**
     * ******************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN CLIENTE REGISTRADO A LA TABLA
     * CLIENTES.
     * ******************************************************************
     * @param rs
     * @return 
     * @throws java.sql.SQLException 
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
    }
    
}
