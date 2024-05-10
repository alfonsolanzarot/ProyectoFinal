package modelo;

import java.sql.Date;

/**
 *
 * @author Alfonso Lanzarot
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

    //CONSTRUCTOR VACÍO.
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

    //CONSTRUCTOR SOBRECARGADO.
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
    public int getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(int idProforma) {
        this.idProforma = idProforma;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    public Date getValidez() {
        return validez;
    }

    public void setValidez(Date validez) {
        this.validez = validez;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getC_postal() {
        return c_postal;
    }

    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getTransporte() {
        return transporte;
    }

    public void setTransporte(Double transporte) {
        this.transporte = transporte;
    }

    public Double getSeguro() {
        return seguro;
    }

    public void setSeguro(Double seguro) {
        this.seguro = seguro;
    }
    
    public String getTipo_precio() {
        return tipo_precio;
    }

    public void setTipo_precio(String tipo_precio) {
        this.tipo_precio = tipo_precio;
    }
    
    public Double getKilos() {
        return kilos;
    }

    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }

    public Double getSuma_subtotal() {
        return suma_subtotal;
    }

    public void setSuma_subtotal(Double suma_subtotal) {
        this.suma_subtotal = suma_subtotal;
    }

    public Double getSuma_iva() {
        return suma_iva;
    }

    public void setSuma_iva(Double suma_iva) {
        this.suma_iva = suma_iva;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
