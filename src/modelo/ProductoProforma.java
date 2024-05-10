package modelo;

/**
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

    

    

    // CONSTRUCTOR VACÍO
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
    }

    // CONSTRUCTOR SOBRECARGADO
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
    }
    
    // GETTERS Y SETTERS

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(int idProforma) {
        this.idProforma = idProforma;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Double getTipo_iva() {
        return tipo_iva;
    }

    public void setTipo_iva(Double tipo_iva) {
        this.tipo_iva = tipo_iva;
    }

    public Double getImporte_iva() {
        return importe_iva;
    }

    public void setImporte_iva(Double importe_iva) {
        this.importe_iva = importe_iva;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
    public boolean isEsServicio() {
        return esServicio;
    }

    public void setEsServicio(boolean esServicio) {
        this.esServicio = esServicio;
    }
    
    public String getFormatoUnidades() {
        return formatoUnidades;
    }

    public void setFormatoUnidades(String formatoUnidades) {
        this.formatoUnidades = formatoUnidades;
    }

}
