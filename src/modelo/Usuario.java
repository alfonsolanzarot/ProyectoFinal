package modelo;

/**
 *
 * @author Alfonso Lanzarot
 */
public class Usuario {

    //ATRIBUTOS
    private int idUsuario;
    private int idRoles;
    private String nombre;
    private String apellidos;
    private String email;
    private String clave;
    private boolean estado;

    //CONSTRUCTOR
    public Usuario() {
        this.idUsuario = 0;
        this.idRoles = 0;
        this.nombre = "";
        this.apellidos = "";
        this.email = "";
        this.clave = "";
        this.estado = false;
    }
    
    //CONSTRUCTOR SOBRECARGADO
    public Usuario(int idUsuario, int idRoles, String nombre, String apellidos, String email, String clave, boolean estado) {
        this.idUsuario = idUsuario;
        this.idRoles = idRoles;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.clave = clave;
        this.estado = estado;
    }
    
    
    //MÉTODOS SETTER AND GETTER
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
