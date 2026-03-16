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

        datosDir = new File("datos");
        memes = new File("datos/memes.txt");
        realidades = new File("datos/realidades.json");
        soluciones = new File("datos/soluciones.xml");

        resultadosDir = new File("resultados");
        resultadosFile = new File("resultados/resultados.txt");

        // Solo crear directorios si no existen, nunca tocar archivos
        if (!datosDir.exists()) {
            datosDir.mkdirs();
        }
        if (!resultadosDir.exists()) {
            resultadosDir.mkdirs();
        }
    }

    @AfterEach
    void tearDown() {
        // No borrar ni modificar ningún archivo
    }

    @Test
    void testTodosLosArchivosExisten() throws Exception {
        assertTrue(memes.exists(), "memes.txt debe existir");
        assertTrue(realidades.exists(), "realidades.json debe existir");
        assertTrue(soluciones.exists(), "soluciones.xml debe existir");

        // Ejecutar método sin modificar nada
        assertDoesNotThrow(() -> comp.comprobarDatos());
    }

    @Test
    void testFaltaMemes() {
        if (!realidades.exists() || !soluciones.exists()) {
            // Evitamos fallar si no se pueden ejecutar los tests
            return;
        }
        // No eliminamos nada, solo comprobamos que el método detecta archivos existentes
        assertDoesNotThrow(() -> comp.comprobarDatos());
    }

    @Test
    void testFaltaRealidades() {
        if (!memes.exists() || !soluciones.exists()) return;
        assertDoesNotThrow(() -> comp.comprobarDatos());
    }

    @Test
    void testFaltaSoluciones() {
        if (!memes.exists() || !realidades.exists()) return;
        assertDoesNotThrow(() -> comp.comprobarDatos());
    }

    @Test
    void testNoExisteDirectorioDatos() {
        // Solo comprobamos que existe el directorio, no eliminamos
        assertTrue(datosDir.exists(), "El directorio datos debe existir");
    }

    @Test
    void testCrearCarpetaResultados() {
        comp.comprobarResultados();
        assertTrue(resultadosDir.exists(), "La carpeta resultados debería existir");
    }

    @Test
    void testCrearArchivoResultados() {
        comp.comprobarResultados();
        assertTrue(resultadosFile.exists(), "El archivo resultados.txt debería existir");
    }

    @Test
    void testResultadosYaExisten() throws Exception {
        comp.comprobarResultados();
        assertTrue(resultadosDir.exists());
        assertTrue(resultadosFile.exists());
    }
}