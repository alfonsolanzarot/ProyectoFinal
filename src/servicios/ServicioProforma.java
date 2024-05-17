package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Proforma;

/**
 * Clase que proporciona servicios relacionados con las proformas registradas en
 * la tabla de proformas. Esta clase incluye un método para asignar los datos de
 * una proforma registrada en la tabla de proformas desde un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioProforma {

    /**
     * Constructor por defecto de la clase ServicioProforma.
     *
     * Este constructor crea una instancia del ServicioCliente.
     */
    public ServicioProforma() {
        // Constructor por defecto
    }

    /**
     * Método para asignar los datos de una proforma registrada en la tabla de
     * proformas desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos de la proforma.
     * @return Proforma con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static Proforma asignarDatosProforma(ResultSet rs) throws SQLException {
        Proforma proforma = new Proforma();
        proforma.setIdProforma(rs.getInt(1));
        proforma.setFecha(rs.getDate(3));
        proforma.setNumero(rs.getString(4));
        proforma.setValidez(rs.getDate(8));
        proforma.setNombre_cliente(rs.getString(5));
        proforma.setTransporte(rs.getDouble(15));
        proforma.setSeguro(rs.getDouble(16));
        proforma.setKilos(rs.getDouble(17));
        proforma.setTotal(rs.getDouble(21));
        proforma.setEstado(rs.getString(23));

        return proforma;
    } // Cierre del método.

} // Cierre de la clase.

