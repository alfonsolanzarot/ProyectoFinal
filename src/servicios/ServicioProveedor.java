package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Proveedor;

/**
 * Clase que proporciona servicios relacionados con los proveedores registrados
 * en la tabla de proveedores. Esta clase incluye un método para asignar los
 * datos de un proveedor registrado en la tabla de proveedores desde un
 * ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioProveedor {

    /**
     * Método para asignar los datos de un proveedor registrado en la tabla de
     * proveedores desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos del proveedor.
     * @return Proveedor con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static Proveedor asignarDatosProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();

        proveedor.setIdProveedor(rs.getInt(1));
        proveedor.setNombre(rs.getString(2));
        proveedor.setNif(rs.getString(3));
        proveedor.setEmail(rs.getString(4));
        proveedor.setDireccion(rs.getString(5));
        proveedor.setTelefono(rs.getString(6));
        proveedor.setMovil(rs.getString(7));
        proveedor.setPoblacion(rs.getString(8));
        proveedor.setC_postal(rs.getString(9));
        proveedor.setWebsite(rs.getString(10));
        proveedor.setProvincia(rs.getString(11));
        proveedor.setPais(rs.getString(12));
        proveedor.setN_comercial(rs.getString(13));
        proveedor.setCondiciones_pago(rs.getString(14));

        return proveedor;
    } // Cierre del método.
} // Cierre de la clase.

