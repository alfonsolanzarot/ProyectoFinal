package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Producto;

/**
 *
 * @author Alfonso Lanzarot
 */
public class ServicioProducto {

    /**
     * ******************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN PRODUCTO REGISTRADO A LA TABLA
     * PRODUCTOS.
     * ****************************************************************** @param
     * rs
     * @param rs
     * @return
     * @throws java.sql.SQLException
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
    }

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
    }

    public static String[] convertirFechaArrayInt(String fecha) {
        String[] resultado = null;
        fecha = fecha.trim();
        if (fecha.contains("/")) {
            resultado = fecha.split("/");
        } else if (fecha.contains("-")){
            resultado = fecha.split("-");
        }
        return resultado;
    }

}
