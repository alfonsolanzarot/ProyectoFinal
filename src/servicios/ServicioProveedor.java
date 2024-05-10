package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Proveedor;

/**
 * 
 * @author Alfonso Lanzarot
 */
public class ServicioProveedor {

    /**
     * ********************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN PROVEEDOR REGISTRADO A LA TABLA
     * PROVEEDORES.
     * ********************************************************************
     * @param rs
     * @return 
     * @throws java.sql.SQLException
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
    }
}
