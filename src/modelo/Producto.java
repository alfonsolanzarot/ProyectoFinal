package modelo;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Producto {

    //VARIABLES
    private int idProducto;
    private String codigo_producto;
    private String descripcion;
    private String formato;
    private Double peso_unitario;
    private Double precio_alto;
    private Double precio_bajo;
    private Double precio_servicio;

    //CONSTRUCTOR VACÍO.
    public Producto() {
        this.idProducto = 0;
        this.codigo_producto = "";
        this.descripcion = "";
        this.formato = "";
        this.peso_unitario = 0.00;
        this.precio_alto = 0.00;
        this.precio_bajo = 0.00;
        this.precio_servicio = 0.00;

    }

    //CONSTRUCTOR SOBRECARGADO.
    public Producto(int idProducto, String codigo_producto, String descripcion, String formato, Double peso_unitario,
            Double precio_alto, Double precio_bajo, Double precio_servicio) {
        this.idProducto = idProducto;
        this.codigo_producto = codigo_producto;
        this.descripcion = descripcion;
        this.formato = formato;
        this.peso_unitario = peso_unitario;
        this.precio_alto = precio_alto;
        this.precio_bajo = precio_bajo;
        this.precio_servicio = precio_servicio;
    }

    //MÉTODOS GETTER Y SETTER
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo_producto;
    }

    public void setCodigo(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Double getPesoUnitario() {
        return peso_unitario;
    }

    public void setPesoUnitario(Double peso_unitario) {
        this.peso_unitario = peso_unitario;
    }

    public Double getPrecioAlto() {
        return precio_alto;
    }

    public void setPrecioAlto(Double precio_alto) {
        this.precio_alto = precio_alto;
    }

    public Double getPrecioBajo() {
        return precio_bajo;
    }

    public void setPrecioBajo(Double precio_bajo) {
        this.precio_bajo = precio_bajo;
    }

    public Double getPrecioServicio() {
        return precio_servicio;
    }

    public void setPrecioServicio(Double precio_servicio) {
        this.precio_servicio = precio_servicio;
    }

}
