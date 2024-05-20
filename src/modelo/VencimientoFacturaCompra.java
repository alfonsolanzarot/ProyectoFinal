package modelo;

/**
 * La clase VencimientoFacturaCompra representa un vencimiento asociado a una
 * factura de compra. Contiene información sobre la fecha de vencimiento,
 * importe pendiente, fecha de pago, importe pagado y estado del vencimiento.
 *
 * @author Alfonso Lanzarot
 */
public class VencimientoFacturaCompra {

    private int idFacturaCompra;
    private String fecha_vencimiento;
    private Double importe_pendiente;
    private String fecha_pago;
    private Double importe_pagado;
    private boolean estado;

    /**
     * Constructor por defecto de la clase VencimientoFacturaCompra. Inicializa
     * los atributos con valores predeterminados.
     */
    public VencimientoFacturaCompra() {
        this.idFacturaCompra = 0;
        this.fecha_vencimiento = "";
        this.importe_pendiente = 0.00;
        this.fecha_pago = "";
        this.importe_pagado = 0.00;
        this.estado = false;
    }

    /**
     * Constructor con parámetros de la clase VencimientoFacturaCompra. Permite
     * establecer los valores de los atributos al crear una instancia de la
     * clase.
     *
     * @param idFacturaCompra El ID de la factura de compra asociada al
     * vencimiento.
     * @param fecha_vencimiento La fecha de vencimiento del vencimiento.
     * @param importe_pendiente El importe pendiente del vencimiento.
     * @param fecha_pago La fecha de pago del vencimiento.
     * @param importe_pagado El importe pagado del vencimiento.
     * @param estado El estado del vencimiento (true si está pagado, false si
     * está pendiente).
     */
    public VencimientoFacturaCompra(int idFacturaCompra, String fecha_vencimiento, Double importe_pendiente, String fecha_pago, Double importe_pagado, boolean estado) {
        this.idFacturaCompra = idFacturaCompra;
        this.fecha_vencimiento = fecha_vencimiento;
        this.importe_pendiente = importe_pendiente;
        this.fecha_pago = fecha_pago;
        this.importe_pagado = importe_pagado;
        this.estado = estado;
    }

    /**
     * Obtiene el ID de la factura de compra asociada al vencimiento.
     *
     * @return El ID de la factura de compra asociada al vencimiento.
     */
    public int getIdFacturaCompra() {
        return idFacturaCompra;
    }

    /**
     * Establece el ID de la factura de compra asociada al vencimiento.
     *
     * @param idFacturaCompra El ID de la factura de compra a establecer.
     */
    public void setIdFacturaCompra(int idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    /**
     * Obtiene la fecha de vencimiento del vencimiento.
     *
     * @return La fecha de vencimiento del vencimiento.
     */
    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    /**
     * Establece la fecha de vencimiento del vencimiento.
     *
     * @param fecha_vencimiento La fecha de vencimiento a establecer.
     */
    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    /**
     * Obtiene el importe pendiente del vencimiento.
     *
     * @return El importe pendiente del vencimiento.
     */
    public Double getImporte_pendiente() {
        return importe_pendiente;
    }

    /**
     * Establece el importe pendiente del vencimiento.
     *
     * @param importe_pendiente El importe pendiente a establecer.
     */
    public void setImporte_pendiente(Double importe_pendiente) {
        this.importe_pendiente = importe_pendiente;
    }

    /**
     * Obtiene la fecha de pago del vencimiento.
     *
     * @return La fecha de pago del vencimiento.
     */
    public String getFecha_pago() {
        return fecha_pago;
    }

    /**
     * Establece la fecha de pago del vencimiento.
     *
     * @param fecha_pago La fecha de pago a establecer.
     */
    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    /**
     * Obtiene el importe pagado del vencimiento.
     *
     * @return El importe pagado del vencimiento.
     */
    public Double getImporte_pagado() {
        return importe_pagado;
    }

    /**
     * Establece el importe pagado del vencimiento.
     *
     * @param importe_pagado El importe pagado a establecer.
     */
    public void setImporte_pagado(Double importe_pagado) {
        this.importe_pagado = importe_pagado;
    }

    /**
     * Verifica si el vencimiento está pagado.
     *
     * @return true si el vencimiento está pagado, false si está pendiente.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Establece el estado del vencimiento.
     *
     * @param estado El estado del vencimiento (true si está pagado, false si
     * está pendiente).
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
