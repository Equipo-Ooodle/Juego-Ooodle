package co.edu.poli.ooodle.modelo;

/**
 * Representa un usuario del sistema.
 * Contiene la información básica necesaria
 * para autenticación y almacenamiento en base de datos.
 * 
 * @author David
 * @version 1.0
 */
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    private int id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Contraseña del usuario.
     * Se almacena en formato encriptado.
     */
    private String contraseña;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor completo del usuario.
     * 
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param contraseña contraseña encriptada
     */
    public Usuario(int id, String nombre, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    /**
     * Constructor sin identificador.
     * 
     * @param nombre nombre del usuario
     * @param contraseña contraseña encriptada
     */
    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el identificador del usuario.
     * 
     * @return id del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return contraseña encriptada
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Define el identificador del usuario.
     * 
     * @param id identificador del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define el nombre del usuario.
     * 
     * @param nombre nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Define la contraseña del usuario.
     * 
     * @param contraseña contraseña encriptada
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}