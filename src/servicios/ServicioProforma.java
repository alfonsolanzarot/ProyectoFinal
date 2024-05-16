
package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Proforma;

/**
 * 
 * @author Alfonso Lanzarot
 */
public class ServicioProforma {

   /**
     * ********************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UNA PROFORMA REGISTRADA A LA TABLA
     * PROFORMAS.
     * ********************************************************************
     * @param rs
     * @return 
     * @throws java.sql.SQLException 
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
    }
    
    
}
