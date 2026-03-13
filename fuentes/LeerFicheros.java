import java.io.*;
import java.nio.file.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import java.util.*;
import org.json.*;

/**
 * Clase encargada de leer los distintos ficheros de datos utilizados
 * por la aplicación y transformar su contenido en estructuras de datos
 * manejables dentro del programa.
 *
 * Permite trabajar con ficheros XML, JSON y de texto.
 */
public class LeerFicheros {

    public static List<String> leerFichero(String ruta) throws Exception {
        if (ruta == null){
            throw new NullPointerException("La ruta no puede ser nula.");
        }
        if (ruta.isEmpty() || ruta.equals(" ")){
            throw new Exception("No se ha introducido ninguna ruta.");
        }

        Path path = Paths.get(ruta);

        List<String> contenido = Files.readAllLines(path);
        if (contenido.isEmpty()){
            throw new Exception("El fichero esta vacío.");
        }

        return contenido;
    }

}