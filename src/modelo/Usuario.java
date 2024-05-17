package modelo;

/**
 * Representa un usuario del sistema. Contiene detalles del usuario como ID,
 * roles, nombre, apellidos, email, clave y estado.
 *
 * @autor Alfonso Lanzarot
 */
public class Usuario {

    // VARIABLES
    private int idUsuario;
    private int idRoles;
    private String nombre;
    private String apellidos;
    private String email;
    private String clave;
    private boolean estado;

    /**
     * Constructor vacío que inicializa un usuario con valores por defecto.
     */
    public Usuario() {
        this.idUsuario = 0;
        this.idRoles = 0;
        this.nombre = "";
        this.apellidos = "";
        this.email = "";
        this.clave = "";
        this.estado = false;
    }

    /**
     * Constructor sobrecargado que inicializa un usuario con valores
     * específicos.
     *
     * @param idUsuario El ID del usuario.
     * @param idRoles El ID del rol del usuario.
     * @param nombre El nombre del usuario.
     * @param apellidos Los apellidos del usuario.
     * @param email El correo electrónico del usuario.
     * @param clave La contraseña del usuario.
     * @param estado El estado del usuario.
     */
    public Usuario(int idUsuario, int idRoles, String nombre, String apellidos, String email, String clave, boolean estado) {
        this.idUsuario = idUsuario;
        this.idRoles = idRoles;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.clave = clave;
        this.estado = estado;
    }

    /**
     * Devuelve el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param idUsuario El ID del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Devuelve el ID del rol del usuario.
     *
     * @return El ID del rol del usuario.
     */
    public int getIdRoles() {
        return idRoles;
    }

    /**
     * Establece el ID del rol del usuario.
     *
     * @param idRoles El ID del rol del usuario.
     */
    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve los apellidos del usuario.
     *
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     *
     * @param apellidos Los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     *
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la clave del usuario.
     *
     * @return La clave del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece la clave del usuario.
     *
     * @param clave La clave del usuario.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Devuelve el estado del usuario.
     *
     * @return El estado del usuario.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Establece el estado del usuario.
     *
     * @param estado El estado del usuario.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
