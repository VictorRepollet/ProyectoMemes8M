/**
 * Clase que representa una puntuación registrada en el juego.
 *
 * Se utiliza para almacenar el nombre del jugador y su puntuación,
 * además de permitir ordenarlas de mayor a menor.
 */
public class Puntuacion implements Comparable<Puntuacion> {
    private final String nombre;
    private final Integer puntuacion;

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
        return Integer.compare(otra.getPuntuacion(), this.getPuntuacion()); // Orden descendente
    }

    /**
     * Representación en cadena para guardar en archivo.
     * @return Cadena en formato "nombre:puntuacion".
     */
    @Override
    public String toString() {
        return getNombre() + ":" + getPuntuacion();
    }
}
