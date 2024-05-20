package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.FacturaCompra;

/**
 * Clase que proporciona servicios relacionados con las facturas de compra
 * registradas en la tabla de facturas de compra. Esta clase incluye un método
 * para asignar los datos de una factura de compra registrada en la tabla de
 * facturas de compra desde un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioFacturaCompra {

    /**
     * Constructor por defecto de la clase ServicioFacturaCompra.
     *
     * Este constructor crea una instancia del ServicioFacturaCompra.
     */
    public ServicioFacturaCompra() {
        // Constructor por defecto
    }

    /**
     * Método para asignar los datos de una factura de compra registrada en la
     * tabla de facturas de compra desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos de la factura de compra.
     * @return Factura de compra con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static FacturaCompra asignarDatosFacturaCompra(ResultSet rs) throws SQLException {
        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.setIdFacturaCompra(rs.getInt(1));
        facturaCompra.setFecha(rs.getString(3));
        facturaCompra.setNumero(rs.getString(4));
        facturaCompra.setNombre_proveedor(rs.getString(5));
        facturaCompra.setDescripcion(rs.getString(6));
        facturaCompra.setTotal(rs.getDouble(12));
        facturaCompra.setDomiciliado(rs.getBoolean(13));
        facturaCompra.setAsociada(rs.getString(15));
        facturaCompra.setEstado(rs.getString(16));

        return facturaCompra;
    } // Cierre del método.

    /**
     * Método que elimina los símbolos de euro y porcentaje de una cadena para
     * que se puedan realizar cálculos correctamente.
     *
     * @param cadena La cadena con valores numéricos y símbolos.
     * @return La cadena con los símbolos de euro, kilo y porcentaje eliminados.
     */
    public static String quitarSimboloEuroPorcentaje(String cadena) {

        String resultado = cadena;
        try {
            if (cadena.contains("€")) {
                resultado = cadena.replace(" €", "");
            } else if (cadena.contains("%")) {
                resultado = cadena.replace("%", "");
            }

            if (resultado.contains(".")) {
                resultado = resultado.replace(".", "");
            }

            resultado = resultado.replace(",", ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    } // Cierre del método.

} // Cierre de la clase.

