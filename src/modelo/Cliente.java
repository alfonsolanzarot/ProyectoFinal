package modelo;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Cliente {

    //VARIABLES
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

    //CONSTRUCTOR VACÍO.
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

    //CONSTRUCTOR SOBRECARGADO.
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

    //MÉTODOS GETTER Y SETTER
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getN_comercial() {
        return n_comercial;
    }

    public void setN_comercial(String n_comercial) {
        this.n_comercial = n_comercial;
    }

    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTipo_precio() {
        return tipo_precio;
    }

    public void setTipo_precio(String tipo_precio) {
        this.tipo_precio = tipo_precio;
    }

}
