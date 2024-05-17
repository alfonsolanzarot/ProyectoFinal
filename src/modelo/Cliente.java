package modelo;

/**
 * Representa un cliente del sistema. Contiene detalles del cliente como ID,
 * nombre, NIF, dirección, población, código postal, provincia, país, nombre
 * comercial, condiciones de pago, email, teléfono, móvil, sitio web y tipo de
 * precio.
 *
 * @autor Alfonso Lanzarot
 */
public class Cliente {

    // VARIABLES
    private int idCliente;
    private String nombre;
    private String nif;
    private String direccion;
    private String poblacion;
    private String c_postal;
    private String provincia;
    private String pais;
    private String n_comercial;
    private String condiciones_pago;
    private String email;
    private String telefono;
    private String movil;
    private String website;
    private String tipo_precio;

    /**
     * Constructor vacío que inicializa un cliente con valores por defecto.
     */
    public Cliente() {
        this.idCliente = 0;
        this.nombre = "";
        this.nif = "";
        this.direccion = "";
        this.poblacion = "";
        this.c_postal = "";
        this.provincia = "";
        this.pais = "";
        this.n_comercial = "";
        this.condiciones_pago = "";
        this.email = "";
        this.telefono = "";
        this.movil = "";
        this.website = "";
        this.tipo_precio = "";
    }

    /**
     * Constructor sobrecargado que inicializa un cliente con valores
     * específicos.
     *
     * @param idCliente ID del cliente.
     * @param nombre Nombre del cliente.
     * @param nif NIF del cliente.
     * @param direccion Dirección del cliente.
     * @param poblacion Población del cliente.
     * @param c_postal Código postal del cliente.
     * @param provincia Provincia del cliente.
     * @param pais País del cliente.
     * @param n_comercial Nombre comercial del cliente.
     * @param condiciones_pago Condiciones de pago del cliente.
     * @param email Email del cliente.
     * @param telefono Teléfono del cliente.
     * @param movil Móvil del cliente.
     * @param website Sitio web del cliente.
     * @param tipo_precio Tipo de precio del cliente.
     */
    public Cliente(int idCliente, String nombre, String nif, String direccion, String poblacion,
            String c_postal, String provincia, String pais, String n_comercial, String condiciones_pago,
            String email, String telefono, String movil, String website, String tipo_precio) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.c_postal = c_postal;
        this.provincia = provincia;
        this.pais = pais;
        this.n_comercial = n_comercial;
        this.condiciones_pago = condiciones_pago;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
        this.website = website;
        this.tipo_precio = tipo_precio;
    }

    // MÉTODOS GETTER Y SETTER
    /**
     * Devuelve el ID del cliente.
     *
     * @return El ID del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param idCliente El ID del cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Devuelve el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el NIF del cliente.
     *
     * @return El NIF del cliente.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del cliente.
     *
     * @param nif El NIF del cliente.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve la dirección del cliente.
     *
     * @return La dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion La dirección del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve la población del cliente.
     *
     * @return La población del cliente.
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Establece la población del cliente.
     *
     * @param poblacion La población del cliente.
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Devuelve el código postal del cliente.
     *
     * @return El código postal del cliente.
     */
    public String getC_postal() {
        return c_postal;
    }

    /**
     * Establece el código postal del cliente.
     *
     * @param c_postal El código postal del cliente.
     */
    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
    }

    /**
     * Devuelve la provincia del cliente.
     *
     * @return La provincia del cliente.
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece la provincia del cliente.
     *
     * @param provincia La provincia del cliente.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Devuelve el país del cliente.
     *
     * @return El país del cliente.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país del cliente.
     *
     * @param pais El país del cliente.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Devuelve el nombre comercial del cliente.
     *
     * @return El nombre comercial del cliente.
     */
    public String getN_comercial() {
        return n_comercial;
    }

    /**
     * Establece el nombre comercial del cliente.
     *
     * @param n_comercial El nombre comercial del cliente.
     */
    public void setN_comercial(String n_comercial) {
        this.n_comercial = n_comercial;
    }

    /**
     * Devuelve las condiciones de pago del cliente.
     *
     * @return Las condiciones de pago del cliente.
     */
    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    /**
     * Establece las condiciones de pago del cliente.
     *
     * @param condiciones_pago Las condiciones de pago del cliente.
     */
    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    /**
     * Devuelve el email del cliente.
     *
     * @return El email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente.
     *
     * @param email El email del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono del cliente.
     *
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     *
     * @param telefono El teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el móvil del cliente.
     *
     * @return El móvil del cliente.
     */
    public String getMovil() {
        return movil;
    }

    /**
     * Establece el móvil del cliente.
     *
     * @param movil El móvil del cliente.
     */
    public void setMovil(String movil) {
        this.movil = movil;
    }

    /**
     * Devuelve el sitio web del cliente.
     *
     * @return El sitio web del cliente.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Establece el sitio web del cliente.
     *
     * @param website El sitio web del cliente.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Devuelve el tipo de precio del cliente.
     *
     * @return El tipo de precio del cliente.
     */
    public String getTipo_precio() {
        return tipo_precio;
    }

    /**
     * Establece el tipo de precio del cliente.
     *
     * @param tipo_precio El tipo de precio del cliente.
     */
    public void setTipo_precio(String tipo_precio) {
        this.tipo_precio = tipo_precio;
    }

}
