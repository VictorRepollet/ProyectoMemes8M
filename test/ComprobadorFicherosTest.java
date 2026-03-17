import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ComprobadorFicherosTest {

    private ComprobadorFicheros comp;

    private File datosDir;
    private File memes;
    private File realidades;
    private File soluciones;

    private File resultadosDir;
    private File resultadosFile;

    @BeforeEach
    void setUp() {

        comp = new ComprobadorFicheros();

        // Directorio datos
        datosDir = new File("datos");
        memes = new File("datos/memes.txt");
        realidades = new File("datos/realidades.json");
        soluciones = new File("datos/soluciones.xml");

        // Directorio resultados
        resultadosDir = new File("resultados");
        resultadosFile = new File("resultados/resultados.txt");

        // Crear directorio datos si no existe
        if (!datosDir.exists()) {
            datosDir.mkdirs();
        }

        // Limpiar archivos de datos
        memes.delete();
        realidades.delete();
        soluciones.delete();

        // Limpiar resultados
        resultadosFile.delete();
        resultadosDir.delete();
    }

    @AfterEach
    void tearDown() {

        memes.delete();
        realidades.delete();
        soluciones.delete();

        resultadosFile.delete();
        resultadosDir.delete();
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

    /**
     * HU2 - Si no existe la carpeta resultados debe crearse
     */
    @Test
    void testCrearCarpetaResultados() {

        comp.comprobarResultados();

        assertTrue(resultadosDir.exists(), "La carpeta resultados debería haberse creado");
    }

    /**
     * HU2 - Si no existe resultados.txt debe crearse
     */
    @Test
    void testCrearArchivoResultados() {

        comp.comprobarResultados();

        assertTrue(resultadosFile.exists(), "El archivo resultados.txt debería haberse creado");
    }

    /**
     * HU2 - Si la carpeta y el archivo ya existen no debe haber problemas
     */
    @Test
    void testResultadosYaExisten() throws Exception {

        resultadosDir.mkdir();
        resultadosFile.createNewFile();

        comp.comprobarResultados();

        assertTrue(resultadosDir.exists());
        assertTrue(resultadosFile.exists());
    }
}