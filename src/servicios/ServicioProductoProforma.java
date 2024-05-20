package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ProductoProforma;

/**
 * Clase que proporciona servicios relacionados con los productos que se
 * integran en la tabla de la factura proforma. Esta clase incluye un método
 * para asignar los datos de un producto registrado en la factura proforma desde
 * un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioProductoProforma {

    /**
     * Constructor por defecto de la clase ServicioProductoProforma.
     *
     * Este constructor crea una instancia del ServicioProductoProforma.
     */
    public ServicioProductoProforma() {
        // Constructor por defecto
    }

    /**
     * Método para asignar los datos de un producto registrado en la factura
     * proforma desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos del producto.
     * @return ProductoProforma con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
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
    } // Cierre del método.

} // Cierre de la clase.

