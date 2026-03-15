import java.io.*;
import java.nio.file.*;

import java.util.*;

/**
 * Clase encargada de leer los distintos ficheros de datos utilizados
 * por la aplicación y transformar su contenido en estructuras de datos
 * manejables dentro del programa.
 *
 * Permite trabajar con ficheros XML, JSON y de texto.
 */
public class LeerFicheros {


    /**
     * Lee el contenido de un fichero de texto y devuelve todas sus líneas.
     * Dejamos sin declarar la ruta para reutilizar el metodo a la hora de hacer las historias de usuarios posteriores.
     *
     * @param ruta ruta del fichero que se desea leer
     * @return lista con todas las líneas del fichero
     * @throws Exception si la ruta es inválida o el fichero está vacío
     */
    public List<String> leerFichero(String ruta) throws Exception {
        Path path = Paths.get(ruta);

        List<String> contenido = Files.readAllLines(path);
        if (contenido.isEmpty()) {
            throw new Exception("El fichero esta vacío.");
        }

        return contenido;
    }

}