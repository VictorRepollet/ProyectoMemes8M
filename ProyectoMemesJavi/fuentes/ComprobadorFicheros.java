import java.io.File;
import java.io.IOException;

/**
 * Clase encargada de comprobar la existencia de archivos
 * y crear los necesarios para el funcionamiento del programa.
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
}