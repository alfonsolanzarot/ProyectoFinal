package modelo;

/**
 * Representa un proveedor del sistema. Contiene detalles del proveedor como ID,
 * nombre, NIF, dirección, población, código postal, provincia, país, nombre
 * comercial, condiciones de pago, email, teléfono, móvil y sitio web.
 *
 * @author Alfonso Lanzarot
 */
public class Proveedor {

    // VARIABLES
    private int idProveedor;
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

    /**
     * Constructor vacío que inicializa un proveedor con valores por defecto.
     */
    public Proveedor() {
        this.idProveedor = 0;
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
    }

    /**
     * Constructor sobrecargado que inicializa un proveedor con valores
     * específicos.
     *
     * @param idProveedor ID del proveedor.
     * @param nombre Nombre del proveedor.
     * @param nif NIF del proveedor.
     * @param direccion Dirección del proveedor.
     * @param poblacion Población del proveedor.
     * @param c_postal Código postal del proveedor.
     * @param provincia Provincia del proveedor.
     * @param pais País del proveedor.
     * @param n_comercial Nombre comercial del proveedor.
     * @param condiciones_pago Condiciones de pago del proveedor.
     * @param email Email del proveedor.
     * @param telefono Teléfono del proveedor.
     * @param movil Móvil del proveedor.
     * @param website Sitio web del proveedor.
     */
    public Proveedor(int idProveedor, String nombre, String nif, String direccion, String poblacion,
            String c_postal, String provincia, String pais, String n_comercial, String condiciones_pago,
            String email, String telefono, String movil, String website) {
        this.idProveedor = idProveedor;
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
    }

    /**
     * Devuelve el ID del proveedor.
     *
     * @return El ID del proveedor.
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el ID del proveedor.
     *
     * @param idProveedor El ID del proveedor.
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Devuelve el nombre del proveedor.
     *
     * @return El nombre del proveedor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param nombre El nombre del proveedor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el NIF del proveedor.
     *
     * @return El NIF del proveedor.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del proveedor.
     *
     * @param nif El NIF del proveedor.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve la dirección del proveedor.
     *
     * @return La dirección del proveedor.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del proveedor.
     *
     * @param direccion La dirección del proveedor.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve la población del proveedor.
     *
     * @return La población del proveedor.
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Establece la población del proveedor.
     *
     * @param poblacion La población del proveedor.
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Devuelve el código postal del proveedor.
     *
     * @return El código postal del proveedor.
     */
    public String getC_postal() {
        return c_postal;
    }

    /**
     * Establece el código postal del proveedor.
     *
     * @param c_postal El código postal del proveedor.
     */
    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
    }

    /**
     * Devuelve la provincia del proveedor.
     *
     * @return La provincia del proveedor.
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece la provincia del proveedor.
     *
     * @param provincia La provincia del proveedor.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Devuelve el país del proveedor.
     *
     * @return El país del proveedor.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país del proveedor.
     *
     * @param pais El país del proveedor.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Devuelve el nombre comercial del proveedor.
     *
     * @return El nombre comercial del proveedor.
     */
    public String getN_comercial() {
        return n_comercial;
    }

    /**
     * Establece el nombre comercial del proveedor.
     *
     * @param n_comercial El nombre comercial del proveedor.
     */
    public void setN_comercial(String n_comercial) {
        this.n_comercial = n_comercial;
    }

    /**
     * Devuelve las condiciones de pago del proveedor.
     *
     * @return Las condiciones de pago del proveedor.
     */
    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    /**
     * Establece las condiciones de pago del proveedor.
     *
     * @param condiciones_pago Las condiciones de pago del proveedor.
     */
    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    /**
     * Devuelve el email del proveedor.
     *
     * @return El email del proveedor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del proveedor.
     *
     * @param email El email del proveedor.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono del proveedor.
     *
     * @return El teléfono del proveedor.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del proveedor.
     *
     * @param telefono El teléfono del proveedor.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el móvil del proveedor.
     *
     * @return El móvil del proveedor.
     */
    public String getMovil() {
        return movil;
    }

    /**
     * Establece el móvil del proveedor.
     *
     * @param movil El móvil del proveedor.
     */
    public void setMovil(String movil) {
        this.movil = movil;
    }

    /**
     * Devuelve el sitio web del proveedor.
     *
     * @return El sitio web del proveedor.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Establece el sitio web del proveedor.
     *
     * @param website El sitio web del proveedor.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

}
