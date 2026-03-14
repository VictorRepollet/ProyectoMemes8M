import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.util.*;
import java.io.*;
import org.json.*;

import static org.junit.jupiter.api.Assertions.*;

public class LeerFicherosTest {

    @TempDir
    Path directorioTemporal;

    /**
     * Comprueba que se leen correctamente las líneas de un fichero
     */
    @Test
    void leerFicheroCorrectamente() throws Exception {

        Path fichero = directorioTemporal.resolve("datos.txt");

        List<String> lineas = List.of(
                "hola",
                "que tal",
                "adios"
        );

        Files.write(fichero, lineas);

        LeerFicheros lector = new LeerFicheros();
        List<String> resultado = lector.leerFichero(fichero.toString());

        assertEquals(3, resultado.size());
        assertEquals("hola", resultado.get(0));
        assertEquals("que tal", resultado.get(1));
        assertEquals("adios", resultado.get(2));
    }

    /**
     * Comprueba que se lanza excepción si el fichero está vacío
     */
    @Test
    void leerFicheroVacioLanzaExcepcion() throws Exception {

        Path fichero = directorioTemporal.resolve("vacio.txt");

        Files.write(fichero, new ArrayList<>());

        LeerFicheros lector = new LeerFicheros();

        Exception e = assertThrows(Exception.class, () -> {
            lector.leerFichero(fichero.toString());
        });

        assertEquals("El fichero esta vacío.", e.getMessage());
    }

    /**
     * Comprueba que falla si la ruta no existe
     */
    @Test
    void leerFicheroRutaInexistente() {

        LeerFicheros lector = new LeerFicheros();

        assertThrows(IOException.class, () -> {
            lector.leerFichero("no_existe.txt");
        });
    }

    /**
     * Comprueba que el JSON devuelve una lista
     */
    @Test
    void obtenerMemesPorJsonDevuelveLista() throws Exception {

        LeerFicheros lector = new LeerFicheros();

        List<MemesRealidades> memes = lector.obtenerMemesPorJson();

        assertNotNull(memes);
    }

    /**
     * Comprueba que la lista de memes no está vacía
     */
    @Test
    void obtenerMemesPorJsonNoVacio() throws Exception {

        LeerFicheros lector = new LeerFicheros();

        List<MemesRealidades> memes = lector.obtenerMemesPorJson();

        assertFalse(memes.isEmpty());
    }
}