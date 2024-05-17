package modelo;

/**
 * Representa un producto del sistema. Contiene detalles del producto como ID,
 * código, descripción, formato, peso unitario, y varios precios (alto, bajo, y
 * de servicio).
 *
 * @author Alfonso Lanzarot
 */
public class Producto {

    // VARIABLES
    private int idProducto;
    private String codigo_producto;
    private String descripcion;
    private String formato;
    private Double peso_unitario;
    private Double precio_alto;
    private Double precio_bajo;
    private Double precio_servicio;

    /**
     * Constructor vacío que inicializa un producto con valores por defecto.
     */
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

    /**
     * Constructor sobrecargado que inicializa un producto con valores
     * específicos.
     *
     * @param idProducto ID del producto.
     * @param codigo_producto Código del producto.
     * @param descripcion Descripción del producto.
     * @param formato Formato del producto.
     * @param peso_unitario Peso unitario del producto.
     * @param precio_alto Precio alto del producto.
     * @param precio_bajo Precio bajo del producto.
     * @param precio_servicio Precio de servicio del producto.
     */
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

    // MÉTODOS GETTER Y SETTER
    /**
     * Devuelve el ID del producto.
     *
     * @return El ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto.
     *
     * @param idProducto El ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve el código del producto.
     *
     * @return El código del producto.
     */
    public String getCodigo() {
        return codigo_producto;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo_producto El código del producto.
     */
    public void setCodigo(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    /**
     * Devuelve la descripción del producto.
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion La descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el formato del producto.
     *
     * @return El formato del producto.
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Establece el formato del producto.
     *
     * @param formato El formato del producto.
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Devuelve el peso unitario del producto.
     *
     * @return El peso unitario del producto.
     */
    public Double getPesoUnitario() {
        return peso_unitario;
    }

    /**
     * Establece el peso unitario del producto.
     *
     * @param peso_unitario El peso unitario del producto.
     */
    public void setPesoUnitario(Double peso_unitario) {
        this.peso_unitario = peso_unitario;
    }

    /**
     * Devuelve el precio alto del producto.
     *
     * @return El precio alto del producto.
     */
    public Double getPrecioAlto() {
        return precio_alto;
    }

    /**
     * Establece el precio alto del producto.
     *
     * @param precio_alto El precio alto del producto.
     */
    public void setPrecioAlto(Double precio_alto) {
        this.precio_alto = precio_alto;
    }

    /**
     * Devuelve el precio bajo del producto.
     *
     * @return El precio bajo del producto.
     */
    public Double getPrecioBajo() {
        return precio_bajo;
    }

    /**
     * Establece el precio bajo del producto.
     *
     * @param precio_bajo El precio bajo del producto.
     */
    public void setPrecioBajo(Double precio_bajo) {
        this.precio_bajo = precio_bajo;
    }

    /**
     * Devuelve el precio de servicio del producto.
     *
     * @return El precio de servicio del producto.
     */
    public Double getPrecioServicio() {
        return precio_servicio;
    }

    /**
     * Establece el precio de servicio del producto.
     *
     * @param precio_servicio El precio de servicio del producto.
     */
    public void setPrecioServicio(Double precio_servicio) {
        this.precio_servicio = precio_servicio;
    }

}
