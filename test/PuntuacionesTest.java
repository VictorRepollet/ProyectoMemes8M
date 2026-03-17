import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase de tests unitarios para HU9 y HU10.
 * HU9: Verificar y registrar puntuación en top 3.
 * HU10: Mostrar mejores puntuaciones y despedida.
 */
public class PuntuacionesTest {

    private static final String RUTA_RESULTADOS = "resultados/resultados.txt";
    private static final String BACKUP_RESULTADOS = "resultados/resultados_backup.txt";

    @BeforeEach
    void setUp() {
        try {
            // Backup
            Path original = Paths.get(RUTA_RESULTADOS);
            Path backup = Paths.get(BACKUP_RESULTADOS);
            if (Files.exists(original)) {
                Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
            }
            // Limpiar
            if (!Files.exists(original.getParent())) {
                Files.createDirectories(original.getParent());
            }
            Files.write(original, Collections.emptyList(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            // Restore
            Path original = Paths.get(RUTA_RESULTADOS);
            Path backup = Paths.get(BACKUP_RESULTADOS);
            if (Files.exists(backup)) {
                Files.copy(backup, original, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(backup);
            } else {
                Files.deleteIfExists(original);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerificarYRegistrarPuntuacion_EntraEnTop3() {
        // Preparar entrada simulada
        String input = "TestUser\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Llamar
        Puntuaciones.verificarYRegistrarPuntuacion(10, scanner);

        // Verificar
        try {
            List<String> lineas = Files.readAllLines(Paths.get(RUTA_RESULTADOS));
            assertEquals(1, lineas.size());
            assertTrue(lineas.get(0).contains("TestUser"));
            assertTrue(lineas.get(0).contains("10"));
        } catch (IOException e) {
            fail("Error al leer archivo");
        }
    }

    @Test
    public void testVerificarYRegistrarPuntuacion_NoEntraEnTop3() {
        try {
            // Preparar con top 3
            List<String> inicial = Arrays.asList("User1:15", "User2:12", "User3:10");
            Files.write(Paths.get(RUTA_RESULTADOS), inicial);

            // Entrada vacía
            String input = "";
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

            // Llamar con baja puntuación
            Puntuaciones.verificarYRegistrarPuntuacion(5, scanner);

            // Verificar no cambió
            List<String> lineas = Files.readAllLines(Paths.get(RUTA_RESULTADOS));
            assertEquals(3, lineas.size());
            assertTrue(lineas.contains("User1:15"));
        } catch (IOException e) {
            fail("Error al manejar archivo");
        }
    }

    @Test
    public void testMostrarMejoresPuntuacionesYDespedida_ConPuntuaciones() {
        try {
            // Preparar puntuaciones
            List<String> inicial = Arrays.asList("User1:15", "User2:12", "User3:10");
            Files.write(Paths.get(RUTA_RESULTADOS), inicial);

            // Capturar salida
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Llamar
            Puntuaciones.mostrarMejoresPuntuacionesYDespedida();

            // Restaurar
            System.setOut(System.out);

            String output = outContent.toString();
            assertTrue(output.contains("Mejores puntuaciones:"));
            assertTrue(output.contains("1. User1 - 15 puntos"));
            assertTrue(output.contains("2. User2 - 12 puntos"));
            assertTrue(output.contains("3. User3 - 10 puntos"));
            assertTrue(output.contains("¡Gracias por jugar!"));
        } catch (IOException e) {
            fail("Error al manejar archivo");
        }
    }

    @Test
    public void testMostrarMejoresPuntuacionesYDespedida_SinPuntuaciones() {
        try {
            // Archivo vacío
            Files.write(Paths.get(RUTA_RESULTADOS), Collections.emptyList());

            // Capturar salida
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Llamar
            Puntuaciones.mostrarMejoresPuntuacionesYDespedida();

            // Restaurar
            System.setOut(System.out);

            String output = outContent.toString();
            assertTrue(output.contains("No hay puntuaciones registradas aún."));
            assertTrue(output.contains("¡Gracias por jugar!"));
        } catch (IOException e) {
            fail("Error al manejar archivo");
        }
    }
}