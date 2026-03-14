import java.util.*;

/**
 * Clase principal que gestiona el flujo del juego Memes 8M.
 * Controla la comprobación de archivos, la carga de datos y la lógica del juego.
 *
 * @author Victor, Alejandro, Javier
 * @version 1.0
 */
public class JuegoMemes {

    /**
     * Método principal que inicia la aplicación.
     * Comprueba los ficheros necesarios, carga los datos y arranca el juego.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
     public static void main(String[] args) {
        start();
    }

    /**
     * Método que ejecuta el programa completo.
     */
   public static void start() {
       ComprobadorFicheros comp = new ComprobadorFicheros();

    System.out.println("================================");
    System.out.println("         JUEGO MEMES 8M");
    System.out.println("================================");

    System.out.println("\nIniciando comprobación de archivos...\n");

    boolean datosOK = comp.comprobarDatos();

    if(!datosOK){
        System.out.println("\nFaltan archivos necesarios.");
        System.out.println("El programa se detiene.");
        return;
    }
    comp.comprobarResultados();
    System.out.println("\nTodos los archivos necesarios existen.");
    System.out.println("El sistema puede iniciar correctamente.");
    // HU3 y HU4: cargar datos
    LeerFicheros gestor = new LeerFicheros();
    List<MemesRealidades> listaMemes = new ArrayList<>();

    try {
        listaMemes = gestor.obtenerMemesPorJson();
    } catch (Exception e) {
        System.out.println("Error al leer los datos: " + e.getMessage());
        return;
    }

    // HU5
    List<Integer> memesUsados = new ArrayList<>();
    mostrarMemeYRealidades(listaMemes, memesUsados);
}



    /**
     * HU5
     * Muestra un meme elegido al azar y lista todas las realidades numeradas.
     * Evita repetir memes que ya hayan salido en rondas anteriores.
     *
     * @param listaMemes  lista con todos los memes cargados del fichero JSON
     * @param memesUsados lista de índices de memes que ya han sido mostrados
     */
 public static int mostrarMemeYRealidades(List<MemesRealidades> listaMemes, List<Integer> memesUsados) {

        Random random = new Random();
        int indice;
        do {
            indice = random.nextInt(listaMemes.size());
        } while(memesUsados.contains(indice));
        memesUsados.add(indice);

        MemesRealidades memeElegido = listaMemes.get(indice);

        System.out.println("\nMEME: " + memeElegido.getName() + "\n");
        System.out.println("¿Qué realidad desmiente este meme?\n");

      //Crear lista de opciones con Strings directamente
        List<String> opciones = new ArrayList<>();
        opciones.add(memeElegido.getReality()); //Opcion correcta

      //Añadimos las fake_realities
        List<String> fakes = new ArrayList<>(memeElegido.getFakeRealities());
        Collections.shuffle(fakes);

        for(String fake : fakes){
            if(opciones.size() < 4) opciones.add(fake);
        }

      //Mezclar todas las opciones
      Collections.shuffle(opciones);

      //Mostrar y localizar la correcta
    Collections.shuffle(opciones);

        int respuestaCorrecta = -1;
        for(int i = 0; i < opciones.size(); i++){
            System.out.println((i+1) + ". " + opciones.get(i));
            if(opciones.get(i).equals(memeElegido.getReality())){
                respuestaCorrecta = i+1;
            }
        }
        System.out.println();

        return respuestaCorrecta;
    }
}