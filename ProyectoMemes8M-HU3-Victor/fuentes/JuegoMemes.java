import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        System.out.println("Iniciando comprobación de archivos...\n");

        boolean datosOK = comprobarDatos();

        if(!datosOK){
            System.out.println("\nFaltan archivos necesarios.");
            System.out.println("El programa se detiene.");
            return;
        }

        comprobarResultados();

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
     * HU1
     * Comprueba si existen los ficheros necesarios para ejecutar la aplicación:
     * memes.txt, realidades.json y soluciones.xml en el directorio datos.
     *
     * @return true si todos los ficheros existen, false si falta alguno
     */
    public static boolean comprobarDatos(){

        boolean todoExiste = true;

        File memes = new File("datos/memes.txt");
        File realidades = new File("datos/realidades.json");
        File soluciones = new File("datos/soluciones.xml");

        if(memes.exists()){
            System.out.println("memes.txt encontrado");
        } else {
            System.out.println("memes.txt NO encontrado");
            todoExiste = false;
        }

        if(realidades.exists()){
            System.out.println("realidades.json encontrado");
        } else {
            System.out.println("realidades.json NO encontrado");
            todoExiste = false;
        }

        if(soluciones.exists()){
            System.out.println("soluciones.xml encontrado");
        } else {
            System.out.println("soluciones.xml NO encontrado");
            todoExiste = false;
        }

        return todoExiste;
    }


    /**
     * HU2
     * Comprueba si existe el directorio resultados y el fichero resultados.txt.
     * Si no existen, los crea automáticamente.
     */
    public static void comprobarResultados(){

        File carpeta = new File("resultados");

        if(!carpeta.exists()){
            System.out.println("\nCarpeta resultados no existe. Creándola...");
            carpeta.mkdir();
        }

        File archivo = new File("resultados/resultados.txt");

        if(archivo.exists()){
            System.out.println("resultados.txt ya existe");
        } else {
            try{
                archivo.createNewFile();
                System.out.println("resultados.txt creado correctamente");
            }
            catch(IOException e){
                System.out.println("Error al crear resultados.txt");
            }
        }
    }


    /**
     * HU5
     * Muestra un meme elegido al azar y lista todas las realidades numeradas.
     * Evita repetir memes que ya hayan salido en rondas anteriores.
     *
     * @param listaMemes  lista con todos los memes cargados del fichero JSON
     * @param memesUsados lista de índices de memes que ya han sido mostrados
     */
    public static void mostrarMemeYRealidades(List<MemesRealidades> listaMemes, List<Integer> memesUsados) {

        Random random = new Random();
        int indice;
        do {
            indice = random.nextInt(listaMemes.size());
        } while (memesUsados.contains(indice));

        memesUsados.add(indice);

        MemesRealidades memeElegido = listaMemes.get(indice);

        System.out.println("\nMEME: " + memeElegido.getName() + "\n");

        System.out.println("¿Qué realidad desmiente este meme?\n");
        for (int i = 0; i < listaMemes.size(); i++) {
            System.out.println((i + 1) + ". " + listaMemes.get(i).getReality());
        }
        System.out.println();
    }
}