package modelo;

/**
 * Representa una factura de compra en el sistema. Contiene información sobre la
 * factura, el proveedor, y otros detalles relevantes.
 *
 * @autor Alfonso Lanzarot
 */
public class FacturaCompra {

    // Variables
    private int idFacturaCompra;
    private int idProveedor;
    private String fecha;
    private String numero;
    private String nombre_proveedor;
    private String descripcion;
    private Double base_imponible;
    private Double tipo_iva;
    private Double iva;
    private Double base_exenta;
    private Double retencion;
    private Double total;
    private boolean domiciliado;
    private String observaciones;
    private String asociada;
    private String estado;

    /**
     * Constructor por defecto que inicializa una factura de compra con valores
     * predeterminados.
     */
    public FacturaCompra() {
        this.idFacturaCompra = 0;
        this.idProveedor = 0;
        this.fecha = "";
        this.numero = "";
        this.nombre_proveedor = "";
        this.descripcion = "";
        this.base_imponible = 0.00;
        this.tipo_iva = 0.00;
        this.iva = 0.00;
        this.base_exenta = 0.00;
        this.retencion = 0.00;
        this.total = 0.00;
        this.domiciliado = false;
        this.observaciones = "";
        this.asociada = "";
        this.estado = "";
    }

    /**
     * Constructor que inicializa una factura de compra con valores
     * especificados.
     *
     * @param idFacturaCompra Identificador de la factura de compra.
     * @param idProveedor Identificador del proveedor.
     * @param fecha Fecha de la factura.
     * @param numero Número de la factura.
     * @param nombre_proveedor Nombre del proveedor.
     * @param descripcion Descripción de la factura.
     * @param base_imponible Base imponible de la factura.
     * @param tipo_iva Tipo de IVA aplicado.
     * @param iva Valor del IVA aplicado.
     * @param base_exenta Base exenta de la factura.
     * @param retencion Retención de la factura.
     * @param total Total de la factura.
     * @param domiciliado Indica si la factura está domiciliada.
     * @param observaciones Observaciones adicionales sobre la factura.
     * @param asociada Información adicional asociada a la factura.
     * @param estado Estado de la factura.
     */
    public FacturaCompra(int idFacturaCompra, int idProveedor, String fecha, String numero, String nombre_proveedor, String descripcion, Double base_imponible,
            Double base_exenta, Double retencion, Double tipo_iva, Double iva, Double total, Boolean domiciliado, String observaciones, String asociada, String estado) {
        this.idFacturaCompra = idFacturaCompra;
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.numero = numero;
        this.nombre_proveedor = nombre_proveedor;
        this.descripcion = descripcion;
        this.base_imponible = base_imponible;
        this.tipo_iva = tipo_iva;
        this.iva = iva;
        this.base_exenta = base_exenta;
        this.retencion = retencion;
        this.total = total;
        this.domiciliado = domiciliado;
        this.observaciones = observaciones;
        this.asociada = asociada;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la factura de compra.
     *
     * @return Identificador de la factura de compra.
     */
    public int getIdFacturaCompra() {
        return idFacturaCompra;
    }

    /**
     * Establece el identificador de la factura de compra.
     *
     * @param idFacturaCompra Identificador de la factura de compra.
     */
    public void setIdFacturaCompra(int idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    /**
     * Obtiene el identificador del proveedor.
     *
     * @return Identificador del proveedor.
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el identificador del proveedor.
     *
     * @param idProveedor Identificador del proveedor.
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Obtiene la fecha de la factura.
     *
     * @return Fecha de la factura.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la factura.
     *
     * @param fecha Fecha de la factura.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el número de la factura.
     *
     * @return Número de la factura.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la factura.
     *
     * @param numero Número de la factura.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el nombre del proveedor.
     *
     * @return Nombre del proveedor.
     */
    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param nombre_proveedor Nombre del proveedor.
     */
    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    /**
     * Obtiene la descripción de la factura.
     *
     * @return Descripción de la factura.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la factura.
     *
     * @param descripcion Descripción de la factura.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la base imponible de la factura.
     *
     * @return Base imponible de la factura.
     */
    public Double getBase_imponible() {
        return base_imponible;
    }

    /**
     * Establece la base imponible de la factura.
     *
     * @param base_imponible Base imponible de la factura.
     */
    public void setBase_imponible(Double base_imponible) {
        this.base_imponible = base_imponible;
    }

    /**
     * Obtiene el tipo de IVA aplicado a la factura.
     *
     * @return Tipo de IVA aplicado a la factura.
     */
    public Double getTipo_iva() {
        return tipo_iva;
    }

    /**
     * Establece el tipo de IVA aplicado a la factura.
     *
     * @param tipo_iva Tipo de IVA aplicado a la factura.
     */
    public void setTipo_iva(Double tipo_iva) {
        this.tipo_iva = tipo_iva;
    }

    /**
     * Obtiene el valor del IVA aplicado a la factura.
     *
     * @return Valor del IVA aplicado a la factura.
     */
    public Double getIva() {
        return iva;
    }

    /**
     * Establece el valor del IVA aplicado a la factura.
     *
     * @param iva Valor del IVA aplicado a la factura.
     */
    public void setIva(Double iva) {
        this.iva = iva;
    }

    /**
     * Obtiene la base exenta de la factura.
     *
     * @return Base exenta de la factura.
     */
    public Double getBase_exenta() {
        return base_exenta;
    }

    /**
     * Establece la base exenta de la factura.
     *
     * @param base_exenta Base exenta de la factura.
     */
    public void setBase_exenta(Double base_exenta) {
        this.base_exenta = base_exenta;
    }

    /**
     * Obtiene la retención de la factura.
     *
     * @return Retención de la factura.
     */
    public Double getRetencion() {
        return retencion;
    }

    /**
     * Establece la retención de la factura.
     *
     * @param retencion Retención de la factura.
     */
    public void setRetencion(Double retencion) {
        this.retencion = retencion;
    }

    /**
     * Obtiene el total de la factura.
     *
     * @return Total de la factura.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Establece el total de la factura.
     *
     * @param total Total de la factura.
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Indica si la factura está domiciliada.
     *
     * @return true si la factura está domiciliada, false en caso contrario.
     */
    public boolean isDomiciliado() {
        return domiciliado;
    }

    /**
     * Establece si la factura está domiciliada.
     *
     * @param domiciliado true si la factura está domiciliada, false en caso
     * contrario.
     */
    public void setDomiciliado(Boolean domiciliado) {
        this.domiciliado = domiciliado;
    }

    /**
     * Obtiene las observaciones adicionales sobre la factura.
     *
     * @return Observaciones adicionales sobre la factura.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones adicionales sobre la factura.
     *
     * @param observaciones Observaciones adicionales sobre la factura.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene información adicional asociada a la factura.
     *
     * @return Información adicional asociada a la factura.
     */
    public String getAsociada() {
        return asociada;
    }

    /**
     * Establece información adicional asociada a la factura.
     *
     * @param asociada Información adicional asociada a la factura.
     */
    public void setAsociada(String asociada) {
        this.asociada = asociada;
    }

    /**
     * Obtiene el estado de la factura.
     *
     * @return Estado de la factura.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la factura.
     *
     * @param estado Estado de la factura.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
