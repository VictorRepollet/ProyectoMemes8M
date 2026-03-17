import java.io.IOException;
import java.util.*;

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

        // 2. Cargar datos (HU3 y HU4)
        List<MemesRealidades> memes = null;
        try {
            memes = LeerFicheros.obtenerMemesPorJson();
        } catch (IOException e) {
            System.out.println("Error al cargar datos del juego: " + e.getMessage());
            return;
        }

        // 3. Lógica del juego (HU5, HU6, HU7)
        int puntuacion = 0;

        for (int ronda = 1; ronda <= 5; ronda++) {
            System.out.println("\n--- Ronda " + ronda + " ---");
            if (JuegoLogica.jugarRonda(memes, teclado)) {
                puntuacion++;
            }
            // HU7: Mostrar marcador actual
            System.out.println("Puntuación actual: " + puntuacion + "/5");
        }

        // HU8, HU9 y HU10: Verificar si entra en top 3 y mostrar mejores puntuaciones
        Puntuaciones.verificarYRegistrarPuntuacion(puntuacion, teclado);
        Puntuaciones.mostrarMejoresPuntuacionesYDespedida();

        System.out.println("================================");
        System.out.println("       TERMINANDO PROGRAMA.");
        System.out.println("================================");

    }
}