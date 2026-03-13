import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Batería de tests para la clase LeerFicheros.
 *
 * Comprueba la lectura de ficheros de texto y la lectura de JSON
 * para generar objetos MemesRealidades.
 */
public class LeerFicherosTest {

    private static final String FICHERO_TEST = "test.txt";

    @BeforeEach
    void crearFichero() throws IOException {
        // Crear un fichero de prueba con contenido
        List<String> contenido = List.of("linea1", "linea2", "linea3");
        Files.write(Paths.get(FICHERO_TEST), contenido);
    }

    @AfterEach
    void borrarFicheros() throws IOException {
        Files.deleteIfExists(Paths.get(FICHERO_TEST));
        Files.deleteIfExists(Paths.get("vacio.txt"));
    }

    // -----------------------------------
    // Tests para leerFichero()
    // -----------------------------------

    @Test
    void leerFicheroCorrectamente() throws Exception {
        List<String> resultado = LeerFicheros.leerFichero(FICHERO_TEST);

        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(3, resultado.size(), "Debe tener 3 líneas");
        assertEquals("linea1", resultado.get(0), "La primera línea debe ser 'linea1'");
    }

    @Test
    void ficheroVacioLanzaExcepcion() throws IOException {
        Path ruta = Paths.get("vacio.txt");
        Files.createFile(ruta); // crear fichero vacío

        Exception ex = assertThrows(Exception.class, () -> {
            LeerFicheros.leerFichero("vacio.txt");
        });

        assertTrue(ex.getMessage().contains("vacío"));
    }

    @Test
    void ficheroNoExiste() {
        assertThrows(IOException.class, () -> {
            LeerFicheros.leerFichero("noExiste.txt");
        });
    }

    // -----------------------------------
    // Tests para obtenerMemesPorJson()
    // -----------------------------------

    @Test
    void cargarMemesDesdeJson() throws Exception {
        // Este test requiere que exista el fichero JSON en ../datos/realidades.json
        List<MemesRealidades> memes = LeerFicheros.obtenerMemesPorJson();

        assertNotNull(memes, "La lista de memes no debe ser nula");
        assertFalse(memes.isEmpty(), "La lista de memes no debe estar vacía");

        MemesRealidades meme = memes.get(0);

        assertNotNull(meme.getId(), "El ID del meme no debe ser nulo");
        assertNotNull(meme.getName(), "El nombre del meme no debe ser nulo");
        assertNotNull(meme.getReality(), "La realidad no debe ser nula");
        assertNotNull(meme.getFakeRealities(), "La lista de realidades falsas no debe ser nula");
    }
}