package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Producto;

/**
 * Clase que proporciona servicios relacionados con los productos o servicios
 * registrados en la tabla 'productos'. Esta clase incluye métodos para asignar
 * los datos de un producto desde un ResultSet, quitar símbolos de euro, kilo y
 * porcentaje, y convertir una fecha en un arreglo de enteros.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioProducto {

    /**
     * Método para asignar los datos de un producto o servicio registrado desde
     * un ResultSet.
     *
     * @param rs ResultSet que contiene los datos del producto.
     * @return Producto con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static Producto asignarDatosProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();

        producto.setIdProducto(rs.getInt(1));
        producto.setCodigo(rs.getString(2));
        producto.setDescripcion(rs.getString(3));
        producto.setFormato(rs.getString(4));
        producto.setPesoUnitario(rs.getDouble(5));
        producto.setPrecioAlto(rs.getDouble(6));
        producto.setPrecioBajo(rs.getDouble(7));
        producto.setPrecioServicio(rs.getDouble(8));

        return producto;
    } // Cierre del método.

    /**
     * Método que elimina los símbolos de euro, kilo y porcentaje de una cadena
     * para que se puedan realizar cálculos correctamente.
     *
     * @param cadena La cadena con valores numéricos y símbolos.
     * @return La cadena con los símbolos de euro, kilo y porcentaje eliminados.
     */
    public static String quitarSimboloEuroKiloPorcentaje(String cadena) {
        String resultado = cadena;
        try {
            if (cadena.contains("kg")) {
                resultado = cadena.replace(" kg", "");
            } else if (cadena.contains("€")) {
                resultado = cadena.replace(" €", "");
            } else if (cadena.contains("%")) {
                resultado = cadena.replace("%", "").trim();
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

    /**
     * Método para convertir una fecha en un arreglo de enteros.
     *
     * @param fecha La fecha en formato string.
     * @return Un arreglo de strings con los componentes de la fecha.
     */
    public static String[] convertirFechaArrayInt(String fecha) {
        String[] resultado = null;
        fecha = fecha.trim();
        if (fecha.contains("/")) {
            resultado = fecha.split("/");
        } else if (fecha.contains("-")) {
            resultado = fecha.split("-");
        }
        return resultado;
    } // Cierre del método.

} // Cierre de la clase.

