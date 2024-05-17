package modelo;

import java.sql.Date;

/**
 * Representa una proforma, que es un documento preliminar de factura que
 * detalla los términos de una futura transacción. Proporciona información sobre
 * el cliente, condiciones de pago, detalles del envío y más.
 *
 * @autor Alfonso Lanzarot
 */
public class Proforma {

    //VARIABLES
    private int idProforma;
    private int idCliente;
    private Date fecha;
    private String numero;
    private String nombre_cliente;
    private String nif;
    private String condiciones_pago;
    private Date validez;
    private String direccion;
    private String poblacion;
    private String c_postal;
    private String provincia;
    private String incoterm;
    private String pais;
    private Double transporte;
    private Double seguro;
    private String tipo_precio;
    private Double kilos;
    private Double suma_subtotal;
    private Double suma_iva;
    private Double descuento;
    private Double total;
    private String observaciones;
    private String estado;

    /**
     * Constructor vacío que inicializa una proforma con valores por defecto.
     */
    public Proforma() {
        this.idProforma = 0;
        this.idCliente = 0;
        this.fecha = null;
        this.numero = "";
        this.nombre_cliente = "";
        this.nif = "";
        this.condiciones_pago = "";
        this.validez = null;
        this.direccion = "";
        this.poblacion = "";
        this.c_postal = "";
        this.provincia = "";
        this.incoterm = "";
        this.pais = "";
        this.transporte = 0.00;
        this.seguro = 0.00;
        this.tipo_precio = "";
        this.kilos = 0.00;
        this.suma_subtotal = 0.00;
        this.suma_iva = 0.00;
        this.descuento = 0.00;
        this.total = 0.00;
        this.observaciones = "";
        this.estado = "";
    }

    /**
     * Constructor sobrecargado que inicializa una proforma con valores
     * específicos.
     *
     * @param idProforma el ID de la proforma
     * @param idCliente el ID del cliente
     * @param fecha la fecha de la proforma
     * @param numero el número de la proforma
     * @param nombre_cliente el nombre del cliente
     * @param nif el NIF del cliente
     * @param condiciones_pago las condiciones de pago
     * @param validez la fecha de validez
     * @param direccion la dirección del cliente
     * @param poblacion la población del cliente
     * @param c_postal el código postal del cliente
     * @param provincia la provincia del cliente
     * @param incoterm el término Incoterm
     * @param pais el país del cliente
     * @param transporte el costo del transporte
     * @param seguro el costo del seguro
     * @param tipo_precio el tipo de precio
     * @param kilos el peso total en kilos
     * @param suma_subtotal el subtotal
     * @param suma_iva la suma del IVA
     * @param descuento el descuento aplicado
     * @param total el total de la proforma
     * @param observaciones las observaciones adicionales
     * @param estado el estado de la proforma
     */
    public Proforma(int idProforma, int idCliente, Date fecha, String numero, String nombre_cliente, String nif, String condiciones_pago, Date validez, String direccion,
            String poblacion, String c_postal, String provincia, String incoterm, String pais, Double transporte, Double seguro, String tipo_precio, Double kilos,
            Double suma_subtotal, Double suma_iva, Double descuento, Double total, String observaciones, String estado) {
        this.idProforma = idProforma;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.numero = numero;
        this.nombre_cliente = nombre_cliente;
        this.nif = nif;
        this.condiciones_pago = condiciones_pago;
        this.validez = validez;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.c_postal = c_postal;
        this.provincia = provincia;
        this.incoterm = incoterm;
        this.pais = pais;
        this.transporte = transporte;
        this.seguro = seguro;
        this.tipo_precio = tipo_precio;
        this.kilos = kilos;
        this.suma_subtotal = suma_subtotal;
        this.suma_iva = suma_iva;
        this.descuento = descuento;
        this.total = total;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    //MÉTODOS GETTER Y SETTER
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
     * Devuelve el ID del cliente.
     *
     * @return el ID del cliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param idCliente el ID del cliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Devuelve la fecha de la proforma.
     *
     * @return la fecha de la proforma
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la proforma.
     *
     * @param fecha la fecha de la proforma
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve el número de la proforma.
     *
     * @return el número de la proforma
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la proforma.
     *
     * @param numero el número de la proforma
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Devuelve el nombre del cliente.
     *
     * @return el nombre del cliente
     */
    public String getNombre_cliente() {
        return nombre_cliente;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre_cliente el nombre del cliente
     */
    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    /**
     * Devuelve el NIF del cliente.
     *
     * @return el NIF del cliente
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del cliente.
     *
     * @param nif el NIF del cliente
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve las condiciones de pago.
     *
     * @return las condiciones de pago
     */
    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    /**
     * Establece las condiciones de pago.
     *
     * @param condiciones_pago las condiciones de pago
     */
    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    /**
     * Devuelve la fecha de validez de la proforma.
     *
     * @return la fecha de validez
     */
    public Date getValidez() {
        return validez;
    }

    /**
     * Establece la fecha de validez de la proforma.
     *
     * @param validez la fecha de validez
     */
    public void setValidez(Date validez) {
        this.validez = validez;
    }

    /**
     * Devuelve la dirección del cliente.
     *
     * @return la dirección del cliente
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion la dirección del cliente
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve la población del cliente.
     *
     * @return la población del cliente
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Establece la población del cliente.
     *
     * @param poblacion la población del cliente
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Devuelve el código postal del cliente.
     *
     * @return el código postal del cliente
     */
    public String getC_postal() {
        return c_postal;
    }

    /**
     * Establece el código postal del cliente.
     *
     * @param c_postal el código postal del cliente
     */
    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
    }

    /**
     * Devuelve la provincia del cliente.
     *
     * @return la provincia del cliente
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece la provincia del cliente.
     *
     * @param provincia la provincia del cliente
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Devuelve el término Incoterm.
     *
     * @return el término Incoterm
     */
    public String getIncoterm() {
        return incoterm;
    }

    /**
     * Establece el término Incoterm.
     *
     * @param incoterm el término Incoterm
     */
    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    /**
     * Devuelve el país del cliente.
     *
     * @return el país del cliente
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país del cliente.
     *
     * @param pais el país del cliente
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Devuelve el costo del transporte.
     *
     * @return el costo del transporte
     */
    public Double getTransporte() {
        return transporte;
    }

    /**
     * Establece el costo del transporte.
     *
     * @param transporte el costo del transporte
     */
    public void setTransporte(Double transporte) {
        this.transporte = transporte;
    }

    /**
     * Devuelve el costo del seguro.
     *
     * @return el costo del seguro
     */
    public Double getSeguro() {
        return seguro;
    }

    /**
     * Establece el costo del seguro.
     *
     * @param seguro el costo del seguro
     */
    public void setSeguro(Double seguro) {
        this.seguro = seguro;
    }

    /**
     * Devuelve el tipo de precio.
     *
     * @return el tipo de precio
     */
    public String getTipo_precio() {
        return tipo_precio;
    }

    /**
     * Establece el tipo de precio.
     *
     * @param tipo_precio el tipo de precio
     */
    public void setTipo_precio(String tipo_precio) {
        this.tipo_precio = tipo_precio;
    }

    /**
     * Devuelve el peso total en kilos.
     *
     * @return el peso total en kilos
     */
    public Double getKilos() {
        return kilos;
    }

    /**
     * Establece el peso total en kilos.
     *
     * @param kilos el peso total en kilos
     */
    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }

    /**
     * Devuelve el subtotal.
     *
     * @return el subtotal
     */
    public Double getSuma_subtotal() {
        return suma_subtotal;
    }

    /**
     * Establece el subtotal.
     *
     * @param suma_subtotal el subtotal
     */
    public void setSuma_subtotal(Double suma_subtotal) {
        this.suma_subtotal = suma_subtotal;
    }

    /**
     * Devuelve la suma del IVA.
     *
     * @return la suma del IVA
     */
    public Double getSuma_iva() {
        return suma_iva;
    }

    /**
     * Establece la suma del IVA.
     *
     * @param suma_iva la suma del IVA
     */
    public void setSuma_iva(Double suma_iva) {
        this.suma_iva = suma_iva;
    }

    /**
     * Devuelve el descuento aplicado.
     *
     * @return el descuento aplicado
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * Establece el descuento aplicado.
     *
     * @param descuento el descuento aplicado
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * Devuelve el total de la proforma.
     *
     * @return el total de la proforma
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Establece el total de la proforma.
     *
     * @param total el total de la proforma
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Devuelve las observaciones adicionales.
     *
     * @return las observaciones adicionales
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones adicionales.
     *
     * @param observaciones las observaciones adicionales
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Devuelve el estado de la proforma.
     *
     * @return el estado de la proforma
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la proforma.
     *
     * @param estado el estado de la proforma
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
