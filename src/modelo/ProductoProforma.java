package modelo;

/**
 * Representa un producto dentro de una proforma. Incluye detalles como código
 * de producto, descripción, cantidad, precio unitario, IVA, subtotal, entre
 * otros.
 *
 * @author Alfonso Lanzarot
 */
public class ProductoProforma {

    // VARIABLES
    private int idProducto;
    private int idProforma;
    private String codigo_producto;
    private String descripcion;
    private Double cantidad;
    private Double precio_unitario;
    private Double tipo_iva;
    private Double importe_iva;
    private Double subtotal;
    private boolean esServicio;
    private String formatoUnidades;

    /**
     * Constructor vacío que inicializa un producto de proforma con valores por
     * defecto.
     */
    public ProductoProforma() {
        this.idProducto = 0;
        this.idProforma = 0;
        this.codigo_producto = "";
        this.descripcion = "";
        this.cantidad = 0.00;
        this.precio_unitario = 0.00;
        this.tipo_iva = 0.00;
        this.importe_iva = 0.00;
        this.subtotal = 0.00;
        this.esServicio = false;
        this.formatoUnidades = "";
    }

    /**
     * Constructor sobrecargado que inicializa un producto de proforma con
     * valores específicos.
     *
     * @param idProducto el ID del producto
     * @param idProforma el ID de la proforma
     * @param codigo_producto el código del producto
     * @param descripcion la descripción del producto
     * @param cantidad la cantidad del producto
     * @param precio_unitario el precio unitario del producto
     * @param tipo_iva el tipo de IVA aplicado al producto
     * @param importe_iva el importe del IVA
     * @param subtotal el subtotal del producto
     */
    public ProductoProforma(int idProducto, int idProforma, String codigo_producto, String descripcion, Double cantidad, Double precio_unitario,
            Double tipo_iva, Double importe_iva, Double subtotal) {
        this.idProducto = idProducto;
        this.idProforma = idProforma;
        this.codigo_producto = codigo_producto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.tipo_iva = tipo_iva;
        this.importe_iva = importe_iva;
        this.subtotal = subtotal;
        this.esServicio = false;
        this.formatoUnidades = "";
    }

    // MÉTODOS GETTERS Y SETTERS
    /**
     * Devuelve el ID del producto.
     *
     * @return el ID del producto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto.
     *
     * @param idProducto el ID del producto
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve el ID de la proforma.
     *
     * @return el ID de la proforma
     */
    public int getIdProforma() {
        return idProforma;
    }

    /**
     * Establece el ID de la proforma.
     *
     * @param idProforma el ID de la proforma
     */
    public void setIdProforma(int idProforma) {
        this.idProforma = idProforma;
    }

    /**
     * Devuelve el código del producto.
     *
     * @return el código del producto
     */
    public String getCodigo_producto() {
        return codigo_producto;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo_producto el código del producto
     */
    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    /**
     * Devuelve la descripción del producto.
     *
     * @return la descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion la descripción del producto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la cantidad del producto.
     *
     * @return la cantidad del producto
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto.
     *
     * @param cantidad la cantidad del producto
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el precio unitario del producto.
     *
     * @return el precio unitario del producto
     */
    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio_unitario el precio unitario del producto
     */
    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * Devuelve el tipo de IVA aplicado al producto.
     *
     * @return el tipo de IVA aplicado al producto
     */
    public Double getTipo_iva() {
        return tipo_iva;
    }

    /**
     * Establece el tipo de IVA aplicado al producto.
     *
     * @param tipo_iva el tipo de IVA aplicado al producto
     */
    public void setTipo_iva(Double tipo_iva) {
        this.tipo_iva = tipo_iva;
    }

    /**
     * Devuelve el importe del IVA.
     *
     * @return el importe del IVA
     */
    public Double getImporte_iva() {
        return importe_iva;
    }

    /**
     * Establece el importe del IVA.
     *
     * @param importe_iva el importe del IVA
     */
    public void setImporte_iva(Double importe_iva) {
        this.importe_iva = importe_iva;
    }

    /**
     * Devuelve el subtotal del producto.
     *
     * @return el subtotal del producto
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal del producto.
     *
     * @param subtotal el subtotal del producto
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Devuelve si el producto es un servicio.
     *
     * @return {@code true} si el producto es un servicio, {@code false} en caso
     * contrario
     */
    public boolean isEsServicio() {
        return esServicio;
    }

    /**
     * Establece si el producto es un servicio.
     *
     * @param esServicio {@code true} si el producto es un servicio,
     * {@code false} en caso contrario
     */
    public void setEsServicio(boolean esServicio) {
        this.esServicio = esServicio;
    }

    /**
     * Devuelve el formato de las unidades del producto.
     *
     * @return el formato de las unidades del producto
     */
    public String getFormatoUnidades() {
        return formatoUnidades;
    }

    /**
     * Establece el formato de las unidades del producto.
     *
     * @param formatoUnidades el formato de las unidades del producto
     */
    public void setFormatoUnidades(String formatoUnidades) {
        this.formatoUnidades = formatoUnidades;
    }
}
