import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clase encargada de verificar la existencia de los recursos necesarios
 * para el correcto funcionamiento de la aplicación.
 *
 * Comprueba que el directorio <code>datos</code> exista y que dentro de él
 * se encuentren los ficheros requeridos por el programa:
 * <ul>
 *     <li>memes.txt</li>
 *     <li>realidades.json</li>
 *     <li>soluciones.xml</li>
 * </ul>
 *
 * En caso de que alguno de estos recursos no exista, se lanzará una excepción
 * indicando cuál es el elemento faltante.
 *
 * @author Victor, Alejandro, Javier
 * @version 1.0
 */
public class ComprobadorFicheros {

    /**
     * HU1 - Verificación de archivos necesarios.
     *
     * Este método comprueba que exista el directorio <code>datos</code> y que
     * dentro de él se encuentren los ficheros necesarios para la ejecución
     * de la aplicación.
     *
     * Si el directorio o alguno de los ficheros no existe, se lanza una
     * excepción {@link FileNotFoundException} indicando qué recurso falta.
     *
     * @throws Exception si el directorio <code>datos</code> o alguno de los
     *                   archivos requeridos no se encuentra en la ruta esperada
     */
    public void comprobarDatos() throws Exception {

        // Referencia al directorio datos
        File directorioDatos = new File("datos");

        // Referencias a los archivos que deben existir en la carpeta datos
        File memes = new File("datos/memes.txt");
        File realidades = new File("datos/realidades.json");
        File soluciones = new File("datos/soluciones.xml");

        // Comprobación del directorio datos
        if (!directorioDatos.exists() || !directorioDatos.isDirectory()) {
            throw new FileNotFoundException("No se encontro el directorio \"datos/\".");
        }

        // Comprobación del archivo memes.txt
        if (!memes.exists()) {
            throw new FileNotFoundException("No se encontro el fichero \"memes.txt\".");
        }

        // Comprobación del archivo realidades.json
        if (!realidades.exists()) {
            throw new FileNotFoundException("No se encontro el fichero \"realidades.json\".");
        }

        // Comprobación del archivo soluciones.xml
        if (!soluciones.exists()) {
            throw new FileNotFoundException("No se encontro el fichero \"soluciones.xml\".");
        }
    }

    /**
     * HU2
     * Comprueba si existe el directorio resultados y el fichero resultados.txt.
     * Si no existen, los crea automáticamente.
     */
    public void comprobarResultados(){

        // Objeto File que representa la carpeta resultados
        File carpeta = new File("../resultados");

        // Si la carpeta no existe se crea
        if(!carpeta.exists()){
            System.out.println("\nCarpeta resultados no existe. Creándola...");
            carpeta.mkdir();
        }

        // Referencia al archivo resultados.txt dentro de la carpeta resultados
        File archivo = new File(carpeta + "/resultados.txt");

        // Si el archivo existe se informa al usuario
        if(!archivo.exists()){
            try{
                // Se crea el archivo si no existe
                archivo.createNewFile();
                System.out.println("resultados.txt creado correctamente");
            }
            catch(IOException e){
                // Manejo de error si ocurre un problema al crear el archivo
                System.out.println("Error al crear resultados.txt");
            }
        } else {
            System.out.println("resultados.txt ya existe");

        }
    }
}