
package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ProductoProforma;

/**
 * 
 * @author Alfonso Lanzarot
 */
public class ServicioProductoProforma {

    /**
     * ******************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN PRODUCTO REGISTRADO A LA TABLA
     * PRODUCTOS.
     * ******************************************************************
     * @param rs
     * @return 
     * @throws java.sql.SQLException 
     */
    public static ProductoProforma asignarDatosProducto(ResultSet rs) throws SQLException {
        ProductoProforma productoProforma = new ProductoProforma();

        productoProforma.setIdProducto(rs.getInt(1));
        productoProforma.setIdProforma(rs.getInt(2));
        productoProforma.setCodigo_producto(rs.getString(3));
        productoProforma.setDescripcion(rs.getString(4));
        productoProforma.setCantidad(rs.getDouble(5));
        productoProforma.setPrecio_unitario(rs.getDouble(6));
        productoProforma.setTipo_iva(rs.getDouble(7));
        productoProforma.setImporte_iva(rs.getDouble(8));
        productoProforma.setSubtotal(rs.getDouble(9));

        return productoProforma;
    }
    
   
}
