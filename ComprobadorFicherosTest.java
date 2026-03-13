import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class ComprobadorFicherosTest {

    private static final String DATOS_DIR = "datos";
    private static final String RESULTADOS_DIR = "resultados";
    private static final String MEMES_FILE = "datos/memes.txt";
    private static final String REALIDADES_FILE = "datos/realidades.json";
    private static final String SOLUCIONES_FILE = "datos/soluciones.xml";
    private static final String RESULTADOS_FILE = "resultados/resultados.txt";

    @BeforeEach
    void setUp() {
        limpiarDirectorios();
        crearDirectoriosDatos();
    }

    private void crearDirectoriosDatos() {
        new File(DATOS_DIR).mkdir();
        try {
            new File(MEMES_FILE).createNewFile();
            new File(REALIDADES_FILE).createNewFile();
            new File(SOLUCIONES_FILE).createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HU1 - Test: cuando todos los archivos existen debe retornar true
     */
    @Test
    void testComprobarDatos_TodosExisten() {
        assertTrue(ComprobadorFicheros.comprobarDatos(), 
            "Debe retornar true cuando todos los archivos existen");
    }

    /**
     * HU1 - Test: cuando falta memes.txt debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaMemes() {
        new File(MEMES_FILE).delete();
        assertFalse(ComprobadorFicheros.comprobarDatos(), 
            "Debe retornar false cuando falta memes.txt");
    }

    /**
     * HU1 - Test: cuando falta realidades.json debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaRealidades() {
        new File(REALIDADES_FILE).delete();
        assertFalse(ComprobadorFicheros.comprobarDatos(), 
            "Debe retornar false cuando falta realidades.json");
    }

    /**
     * HU1 - Test: cuando falta soluciones.xml debe retornar false
     */
    @Test
    void testComprobarDatos_FaltaSoluciones() {
        new File(SOLUCIONES_FILE).delete();
        assertFalse(ComprobadorFicheros.comprobarDatos(), 
            "Debe retornar false cuando falta soluciones.xml");
    }

    /**
     * HU2 - Test: crea la carpeta y archivo de resultados si no existen
     */
    @Test
    void testComprobarResultados_CreaDirectorioYArchivo() {
        // Verificar que no existan antes
        File carpeta = new File(RESULTADOS_DIR);
        if(carpeta.exists()) {
            File[] files = carpeta.listFiles();
            if(files != null) {
                for(File f : files) f.delete();
            }
            carpeta.delete();
        }

        // Ejecutar método
        ComprobadorFicheros.comprobarResultados();

        // Verificar que se crearon
        assertTrue(carpeta.exists(), "Debe crear la carpeta resultados");
        File archivo = new File(RESULTADOS_FILE);
        assertTrue(archivo.exists(), "Debe crear el archivo resultados.txt");
    }

    /**
     * HU2 - Test: cuando ya existen no los recrea
     */
    @Test
    void testComprobarResultados_YaExisten() {
        // Crear archivo con contenido
        File carpeta = new File(RESULTADOS_DIR);
        carpeta.mkdir();
        File archivo = new File(RESULTADOS_FILE);
        try {
            archivo.createNewFile();
            // Escribir contenido de prueba
            java.nio.file.Files.write(archivo.toPath(), "contenido existente".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar método
        ComprobadorFicheros.comprobarResultados();

        // Verificar que todavía existen
        assertTrue(carpeta.exists());
        assertTrue(archivo.exists());
    }

    @AfterEach
    void tearDown() {
        limpiarDirectorios();
    }

    private void limpiarDirectorios() {
        // Limpiar datos
        new File(MEMES_FILE).delete();
        new File(REALIDADES_FILE).delete();
        new File(SOLUCIONES_FILE).delete();
        File datosDir = new File(DATOS_DIR);
        if(datosDir.exists()) datosDir.delete();

        // Limpiar resultados
        File archivoResultados = new File(RESULTADOS_FILE);
        if(archivoResultados.exists()) archivoResultados.delete();
        File resultadosDir = new File(RESULTADOS_DIR);
        if(resultadosDir.exists()) resultadosDir.delete();
    }
}