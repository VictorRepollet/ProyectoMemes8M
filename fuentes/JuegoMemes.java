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
     * Su función es llamar al método {@code start()} que contiene
     * la lógica principal de ejecución del juego.
     *
     * @param args argumentos de línea de comandos (no utilizados en esta aplicación)
     */
    public static void main(String[] args) {
        start();
    }

    /**
     * Método encargado de ejecutar el flujo principal del programa.
     *
     * En este método se realizan las acciones iniciales del juego,
     * como mostrar la cabecera del programa y gestionar el inicio
     * y finalización de la ejecución.
     *
     * En futuras ampliaciones del programa, aquí se incluirán
     * operaciones como:
     * <ul>
     *     <li>Comprobación de los ficheros necesarios</li>
     *     <li>Carga de datos del juego</li>
     *     <li>Ejecución de la lógica principal del juego</li>
     * </ul>
     */
    public static void start() {

        System.out.println("================================");
        System.out.println("         JUEGO MEMES 8M");
        System.out.println("================================");

        System.out.println("================================");
        System.out.println("       TERMINANDO PROGRAMA.");
        System.out.println("================================");

    }
}