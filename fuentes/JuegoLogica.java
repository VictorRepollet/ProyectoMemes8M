import java.util.*;

/**
 * Clase que maneja la lógica del juego de memes.
 * Contiene métodos para ejecutar rondas individuales del juego.
 */
public class JuegoLogica {

    private static Set<Integer> usados = new HashSet<>();
    private static Random random = new Random();

    /**
     * Ejecuta una ronda del juego: selecciona meme, muestra opciones, obtiene elección y verifica.
     * @param memes Lista de memes disponibles
     * @param teclado Scanner para entrada del usuario
     * @return true si la respuesta fue correcta, false en caso contrario
     */
    public static boolean jugarRonda(List<MemesRealidades> memes, Scanner teclado) {
        // Seleccionar meme al azar sin repetir
        int indice;
        do {
            indice = random.nextInt(memes.size());
        } while (usados.contains(indice));
        usados.add(indice);
        MemesRealidades memeActual = memes.get(indice);

        // Mostrar meme
        System.out.println("Meme: " + memeActual.getName());

        // Preparar opciones: realidad correcta + falsas, en orden aleatorio
        List<String> opciones = new ArrayList<>();
        opciones.add(memeActual.getReality());
        opciones.addAll(memeActual.getFakeRealities());
        Collections.shuffle(opciones);

        // Mostrar lista numerada
        System.out.println("Elige la realidad que desmiente este meme:");
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + opciones.get(i));
        }

        // Obtener elección del usuario
        int eleccion = -1;
        while (eleccion < 1 || eleccion > opciones.size()) {
            System.out.print("Tu elección (1-" + opciones.size() + "): ");
            try {
                eleccion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                eleccion = -1;
            }
        }

        // Verificar respuesta
        boolean correcto = opciones.get(eleccion - 1).equals(memeActual.getReality());
        if (correcto) {
            System.out.println("¡Correcto! +1 punto");
        } else {
            System.out.println("Incorrecto. La respuesta correcta era: " + memeActual.getReality());
        }

        return correcto;
    }
}