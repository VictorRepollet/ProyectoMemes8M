import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class ComprobadorFicherosTest {

    private static final ComprobadorFicheros comp = new ComprobadorFicheros();

    private String originalBaseDir;
    private Path tempRoot;

    @BeforeEach
    void setUp() throws IOException {
        // Evitar que los tests modifiquen el proyecto: trabajar en un directorio temporal
        originalBaseDir = System.getProperty("comprobadorficheros.baseDir");
        tempRoot = Files.createTempDirectory("comprobadorficheros-test-");
        System.setProperty("comprobadorficheros.baseDir", tempRoot.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (originalBaseDir != null) {
            System.setProperty("comprobadorficheros.baseDir", originalBaseDir);
        } else {
            System.clearProperty("comprobadorficheros.baseDir");
        }

        if (tempRoot != null && Files.exists(tempRoot)) {
            deleteRecursively(tempRoot);
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.notExists(path)) {
            return;
        }
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ignore) {
                        // Ignorar errores de limpieza de tests
                    }
                });
    }

    private void createDatosFiles(boolean memes, boolean realidades, boolean soluciones) throws IOException {
        Path datosDir = tempRoot.resolve("datos");
        Files.createDirectories(datosDir);
        if (memes) {
            Files.createFile(datosDir.resolve("memes.txt"));
        }
        if (realidades) {
            Files.createFile(datosDir.resolve("realidades.json"));
        }
        if (soluciones) {
            Files.createFile(datosDir.resolve("soluciones.xml"));
        }
    }

    /**
     * HU1 - Test: cuando todos los archivos existen debe retornar true
     */
    @Test
    void testComprobarDatos_TodosExisten() throws IOException {
        createDatosFiles(true, true, true);
        assertTrue(comp.comprobarDatos(),
                "Debe retornar true cuando todos los archivos existen");
    }

    /**
     * HU1 - Test: cuando falta memes.txt debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaMemes() throws IOException {
        createDatosFiles(false, true, true);
        assertFalse(comp.comprobarDatos(),
                "Debe retornar false cuando falta memes.txt");
    }

    /**
     * HU1 - Test: cuando falta realidades.json debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaRealidades() throws IOException {
        createDatosFiles(true, false, true);
        assertFalse(comp.comprobarDatos(),
                "Debe retornar false cuando falta realidades.json");
    }

    /**
     * HU1 - Test: cuando falta soluciones.xml debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaSoluciones() throws IOException {
        createDatosFiles(true, true, false);
        assertFalse(comp.comprobarDatos(),
                "Debe retornar false cuando falta soluciones.xml");
    }
}