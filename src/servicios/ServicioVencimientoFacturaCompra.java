package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.VencimientoFacturaCompra;

/**
 * La clase ServicioVencimientoFacturaCompra proporciona métodos para trabajar
 * con datos de vencimientos de factura de compra. Está diseñada para asignar
 * datos de vencimientos desde un ResultSet.
 *
 * @author Alfonso Lanzarot
 */
public class ServicioVencimientoFacturaCompra {

    /**
     * Constructor por defecto de la clase ServicioVencimientoFacturaCompra.
     *
     * Este constructor crea una instancia del ServicioVencimientoFacturaCompra.
     */
    public ServicioVencimientoFacturaCompra() {
        // Constructor por defecto
    }

    /**
     * Método para asignar los datos de un producto registrado en la factura de
     * compra desde un ResultSet.
     *
     * @param rs ResultSet que contiene los datos del vencimiento.
     * @return VencimientoFacturaCompra con los datos asignados.
     * @throws SQLException si hay un error al acceder a los datos del
     * ResultSet.
     */
    public static VencimientoFacturaCompra asignarDatosVencimiento(ResultSet rs) throws SQLException {
        VencimientoFacturaCompra vencimientoFacturaCompra = new VencimientoFacturaCompra();

        vencimientoFacturaCompra.setIdFacturaCompra(rs.getInt(1));
        vencimientoFacturaCompra.setFecha_vencimiento(rs.getString(2));
        vencimientoFacturaCompra.setImporte_pendiente(rs.getDouble(3));
        vencimientoFacturaCompra.setFecha_pago(rs.getString(4));
        vencimientoFacturaCompra.setImporte_pagado(rs.getDouble(5));
        vencimientoFacturaCompra.setEstado(rs.getBoolean(6));

        return vencimientoFacturaCompra;
    } // Cierre del método.

}
