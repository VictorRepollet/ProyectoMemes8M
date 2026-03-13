import java.io.File;
import java.io.IOException;

/**
 * Clase encargada de comprobar la existencia de archivos
 * y crear los necesarios para el funcionamiento del programa.
 *
 * Esta clase contiene métodos estáticos que verifican la presencia
 * de los archivos requeridos por la aplicación y crean los recursos
 * necesarios en caso de que no existan.
 *
 * @author Victor, Alejandro, Javier
 * @version 1.0
 */
public class ComprobadorFicheros {

    /**
     * HU1
     * Comprueba si existen los ficheros necesarios para ejecutar la aplicación:
     * memes.txt, realidades.json y soluciones.xml en el directorio datos.
     *
     * @return true si todos los ficheros existen, false si falta alguno
     */
    public static boolean comprobarDatos(){

        // Variable que indica si todos los archivos existen
        boolean todoExiste = true;

        // Referencias a los archivos que deben existir en la carpeta datos
        File memes = new File("datos/memes.txt");
        File realidades = new File("datos/realidades.json");
        File soluciones = new File("datos/soluciones.xml");

        // Comprobación del archivo memes.txt
        if(memes.exists()){
            System.out.println("memes.txt encontrado");
        } else {
            System.out.println("memes.txt NO encontrado");
            todoExiste = false;
        }

        // Comprobación del archivo realidades.json
        if(realidades.exists()){
            System.out.println("realidades.json encontrado");
        } else {
            System.out.println("realidades.json NO encontrado");
            todoExiste = false;
        }

        // Comprobación del archivo soluciones.xml
        if(soluciones.exists()){
            System.out.println("soluciones.xml encontrado");
        } else {
            System.out.println("soluciones.xml NO encontrado");
            todoExiste = false;
        }

        // Devuelve el resultado final de la comprobación
        return todoExiste;
    }

}