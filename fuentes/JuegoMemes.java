import java.util.Scanner;

/**
 * Clase principal de la aplicación Memes 8M.
 *
 * Se encarga de iniciar la ejecución del programa y controlar
 * el flujo general del juego. Desde aquí se lanza el método
 * principal que posteriormente delega la ejecución al método
 * {@code start()}, donde se gestiona la lógica inicial del programa.
 *
 * @author Victor, Alejandro, Javier
 * @version 1.0
 */
public class JuegoMemes {

    /**
     * Método principal de entrada de la aplicación.
     *
     * Este método es el punto de inicio del programa en Java.
     * Crea el {@link Scanner} que se utilizará durante toda la ejecución
     * y lo cierra al finalizar, evitando fugas de recursos.
     *
     * @param args argumentos de línea de comandos (no utilizados en esta aplicación)
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        start(teclado);
        teclado.close(); // se cierra aquí, al final de todo
    }

    /**
     * Método encargado de ejecutar el flujo principal del programa.
     *
     * En este método se realizan las acciones del juego en el siguiente orden:
     * <ol>
     *     <li>Mostrar cabecera del programa</li>
     *     <li>Comprobar que los ficheros necesarios existen</li>
     *     <li>Cargar los datos del juego</li>
     *     <li>Ejecutar la lógica principal del juego</li>
     *     <li>Guardar la puntuación final en el fichero de resultados</li>
     * </ol>
     *
     * Si algún fichero necesario no existe, el programa muestra un mensaje
     * de error y termina la ejecución.
     *
     * @param teclado {@link Scanner} compartido para la lectura de entrada del usuario
     */
    public static void start(Scanner teclado) {

        System.out.println("================================");
        System.out.println("         JUEGO MEMES 8M");
        System.out.println("================================");

        // 1. Comprobar ficheros necesarios
        try {
            ComprobadorFicheros comprobador = new ComprobadorFicheros();
            comprobador.comprobarDatos();
            comprobador.comprobarResultados();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return; // si faltan ficheros, no tiene sentido continuar
        }

        // 2. Cargar datos
        // List<MemesRealidades> memes = LeerFicheros.obtenerMemesPorJson();
        // (descomenta cuando vayas a implementar el juego)

        // 3. Lógica del juego
        // (aquí irán las HU6, HU7, HU8...)
        int puntuacion = 0; // provisional hasta que implementes el juego

        // 4. Guardar puntuación al final
        try {
            LeerFicheros.escribirPuntuaciones(puntuacion, teclado);
        } catch (Exception e) {
            System.out.println("Error al guardar puntuacion: " + e.getMessage());
        }

        System.out.println("================================");
        System.out.println("       TERMINANDO PROGRAMA.");
        System.out.println("================================");
    }
}