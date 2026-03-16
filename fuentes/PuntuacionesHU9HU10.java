import java.io.*;
import java.util.*;

/**
 * Clase que maneja las historias de usuario HU9 y HU10 del juego de memes.
 * HU9: Verifica si la puntuación del usuario entra en el top 3 y registra el nombre si es necesario.
 * HU10: Muestra la lista de mejores puntuaciones y se despide.
 */
public class PuntuacionesHU9HU10 {
    /** Ruta al archivo donde se almacenan las mejores puntuaciones. */
    private static final String RUTA_RESULTADOS = "resultados/resultados.txt";

    /**
     * Clase interna que representa una puntuación con nombre y valor.
     * Implementa Comparable para ordenar por puntuación descendente.
     */
    public static class Puntuacion implements Comparable<Puntuacion> {
        private String nombre;
        private Integer puntuacion;

        /**
         * Constructor para crear una nueva puntuación.
         * @param nombre El nombre del usuario.
         * @param puntuacion La puntuación obtenida.
         */
        public Puntuacion(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }

        /** @return El nombre del usuario. */
        public String getNombre() { return nombre; }

        /** @return La puntuación obtenida. */
        public Integer getPuntuacion() { return puntuacion; }

        /**
         * Compara dos puntuaciones por valor descendente.
         * @param otra La otra puntuación a comparar.
         * @return Valor negativo si esta puntuación es mayor, positivo si es menor.
         */
        @Override
        public int compareTo(Puntuacion otra) {
            return Integer.compare(otra.puntuacion, this.puntuacion); // Orden descendente
        }

        /**
         * Representación en cadena para guardar en archivo.
         * @return Cadena en formato "nombre:puntuacion".
         */
        @Override
        public String toString() {
            return nombre + ":" + puntuacion;
        }
    }

    /**
     * HU9: Verifica si la puntuación del usuario entra en el top 3.
     * Si entra, pide el nombre y registra la puntuación actualizando el archivo.
     * @param puntuacionUsuario La puntuación final del usuario.
     * @param scanner El Scanner para leer entrada del usuario.
     */
    public static void verificarYRegistrarPuntuacion(int puntuacionUsuario, Scanner scanner) {
        // Cargar las mejores puntuaciones actuales
        List<Puntuacion> mejores = cargarMejoresPuntuaciones();
        // Verificar si entra en top 3: si hay menos de 3 o supera la más baja
        boolean entraEnTop3 = mejores.size() < 3 || puntuacionUsuario > mejores.get(mejores.size() - 1).getPuntuacion();

        if (entraEnTop3) {
            // Pedir nombre al usuario
            System.out.print("¡Felicidades! Estás en el top 3. Ingresa tu nombre: ");
            String nombreUsuario = scanner.nextLine().trim();
            if (nombreUsuario.isEmpty()) {
                nombreUsuario = "Anónimo"; // Valor por defecto si está vacío
            }

            // Agregar nueva puntuación, ordenar y limitar a top 3
            mejores.add(new Puntuacion(nombreUsuario, puntuacionUsuario));
            Collections.sort(mejores);
            if (mejores.size() > 3) {
                mejores = mejores.subList(0, 3);
            }

            // Guardar en archivo
            guardarMejoresPuntuaciones(mejores);
        }
    }

    /**
     * HU10: Muestra la lista de mejores puntuaciones y un mensaje de despedida.
     * Carga las puntuaciones desde el archivo y las imprime en consola.
     */
    public static void mostrarMejoresPuntuacionesYDespedida() {
        // Cargar las mejores puntuaciones
        List<Puntuacion> mejores = cargarMejoresPuntuaciones();
        System.out.println("\nMejores puntuaciones:");
        if (mejores.isEmpty()) {
            System.out.println("No hay puntuaciones registradas aún.");
        } else {
            // Mostrar lista numerada
            for (Integer i = 0; i < mejores.size(); i++) {
                System.out.println((i + 1) + ". " + mejores.get(i).getNombre() + " - " + mejores.get(i).getPuntuacion() + " puntos");
            }
        }
        // Mensaje de despedida
        System.out.println("¡Gracias por jugar!");
    }

    /**
     * Método auxiliar privado para cargar las mejores puntuaciones desde el archivo.
     * Lee el archivo línea por línea, parsea y ordena las puntuaciones.
     * @return Lista de las top 3 puntuaciones ordenadas descendentemente.
     */
    private static List<Puntuacion> cargarMejoresPuntuaciones() {
        List<Puntuacion> mejores = new ArrayList<>();
        File archivo = new File(RUTA_RESULTADOS);
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    // Dividir línea en nombre y puntuación
                    String[] partes = linea.split(":");
                    if (partes.length == 2) {
                        try {
                            String nombre = partes[0];
                            int puntuacion = Integer.parseInt(partes[1]);
                            mejores.add(new Puntuacion(nombre, puntuacion));
                        } catch (NumberFormatException e) {
                            System.out.println("Error en formato de línea: " + linea);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer resultados: " + e.getMessage());
            }
        }
        // Ordenar y limitar a top 3
        Collections.sort(mejores);
        return mejores.size() > 3 ? mejores.subList(0, 3) : mejores;
    }

    /**
     * Método auxiliar privado para guardar las mejores puntuaciones en el archivo.
     * Escribe cada puntuación en una línea en formato "nombre:puntuacion".
     * @param mejores La lista de puntuaciones a guardar.
     */
    private static void guardarMejoresPuntuaciones(List<Puntuacion> mejores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_RESULTADOS))) {
            for (Puntuacion p : mejores) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar resultados: " + e.getMessage());
        }
    }
}