import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ComprobadorFicherosTest {

    private ComprobadorFicheros comp;

    private File datosDir;
    private File memes;
    private File realidades;
    private File soluciones;

    @BeforeEach
    void setUp() throws IOException {

        comp = new ComprobadorFicheros();

        datosDir = new File("../datos");
        memes = new File("../datos/memes.txt");
        realidades = new File("../datos/realidades.json");
        soluciones = new File("../datos/soluciones.xml");

        // Crear directorio datos si no existe
        if (!datosDir.exists()) {
            datosDir.mkdirs();
        }

        // Eliminar posibles archivos previos
        memes.delete();
        realidades.delete();
        soluciones.delete();
    }

    @AfterEach
    void tearDown() {

        memes.delete();
        realidades.delete();
        soluciones.delete();
    }

    /**
     * HU1 - Cuando todos los archivos existen no debe lanzar excepción
     */
    @Test
    void testTodosLosArchivosExisten() throws Exception {

        memes.createNewFile();
        realidades.createNewFile();
        soluciones.createNewFile();

        try {
            comp.comprobarDatos();
        } catch (Exception e) {
            fail("No debería lanzar excepción cuando todos los archivos existen");
        }
    }

    /**
     * HU1 - Cuando falta memes.txt debe lanzar FileNotFoundException
     */
    @Test
    void testFaltaMemes() throws Exception {

        realidades.createNewFile();
        soluciones.createNewFile();

        try {
            comp.comprobarDatos();
            fail("Debería lanzar excepción porque falta memes.txt");
        } catch (FileNotFoundException e) {
            assertTrue(e.getMessage().contains("memes.txt"));
        }
    }

    /**
     * HU1 - Cuando falta realidades.json debe lanzar FileNotFoundException
     */
    @Test
    void testFaltaRealidades() throws Exception {

        memes.createNewFile();
        soluciones.createNewFile();

        try {
            comp.comprobarDatos();
            fail("Debería lanzar excepción porque falta realidades.json");
        } catch (FileNotFoundException e) {
            assertTrue(e.getMessage().contains("realidades.json"));
        }
    }

    /**
     * HU1 - Cuando falta soluciones.xml debe lanzar FileNotFoundException
     */
    @Test
    void testFaltaSoluciones() throws Exception {

        memes.createNewFile();
        realidades.createNewFile();

        try {
            comp.comprobarDatos();
            fail("Debería lanzar excepción porque falta soluciones.xml");
        } catch (FileNotFoundException e) {
            assertTrue(e.getMessage().contains("soluciones.xml"));
        }
    }

    /**
     * HU1 - Cuando no existe el directorio datos debe lanzar excepción
     */
    @Test
    void testNoExisteDirectorioDatos() {

        datosDir.delete();

        try {
            comp.comprobarDatos();
            fail("Debería lanzar excepción porque no existe el directorio datos");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("datos"));
        }
    }
}