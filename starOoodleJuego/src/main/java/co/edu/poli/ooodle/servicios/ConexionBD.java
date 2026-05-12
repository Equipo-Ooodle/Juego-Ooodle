package co.edu.poli.ooodle.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión
 * con la base de datos de la aplicación.
 * <p>
 * Implementa el patrón Singleton para garantizar
 * que exista una única instancia de conexión
 * durante la ejecución del programa.
 * </p>
 */
public class ConexionBD {

    /**
     * Instancia única de la clase.
     */
    private static ConexionBD instancia;

    /**
     * Conexión activa con la base de datos.
     */
    private Connection conexion;

    /**
     * URL de conexión a la base de datos MySQL.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/ooodle";

    /**
     * Usuario de acceso a la base de datos.
     */
    private static final String USER = "root";

    /**
     * Contraseña de acceso a la base de datos.
     */
    private static final String PASSWORD = "Lony1234";

    /**
     * Constructor privado que inicializa
     * la conexión con la base de datos.
     * <p>
     * Carga el driver de MySQL y establece
     * la conexión inicial.
     * </p>
     */
    private ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    /**
     * Obtiene la instancia única de la clase.
     * <p>
     * Si la instancia no existe, se crea.
     * </p>
     *
     * @return instancia única de {@code ConexionBD}
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Retorna la conexión activa con la base de datos.
     * <p>
     * Si la conexión está cerrada o no existe,
     * se crea nuevamente.
     * </p>
     *
     * @return conexión activa de tipo {@link Connection}
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión", e);
        }
        return conexion;
    }
}