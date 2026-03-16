import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.*;
import java.util.*;

public class PuntuacionesHU9HU10Test {

    // Archivo para pruebas, usando el real pero guardando/restaurando contenido
    private File archivoTemporal;
    private String contenidoOriginal;

    @Before
    public void setUp() {
        // Usar el archivo real de la clase
        archivoTemporal = new File("resultados/resultados.txt");
        // Guardar contenido original si existe
        if (archivoTemporal.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoTemporal))) {
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                contenidoOriginal = sb.toString();
            } catch (IOException e) {
                contenidoOriginal = "";
            }
        } else {
            contenidoOriginal = null;
        }
    }

    @After
    public void tearDown() {
        // Restaurar contenido original o eliminar si no existía
        if (contenidoOriginal != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {
                bw.write(contenidoOriginal);
            } catch (IOException e) {
                // Ignorar
            }
        } else if (archivoTemporal.exists()) {
            archivoTemporal.delete();
        }
    }

    @Test
    public void testVerificaTop3() {
        // Test para HU9: Verificar si la puntuación se registra en el top 3
        // Este test verifica que cuando una puntuación entra en top 3, se pide el nombre y se registra

        // Preparar archivo temporal con puntuaciones existentes
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {
            bw.write("Jugador1:100\n");
            bw.write("Jugador2:80\n");
            bw.write("Jugador3:60\n");
        } catch (IOException e) {
            fail("Error al preparar archivo temporal");
        }

        // Simular entrada del usuario con Scanner
        String input = "NuevoJugador\n"; // Nombre que ingresa el usuario
        Scanner scanner = new Scanner(new StringReader(input));

        // Llamar al método con puntuación que entra en top 3 (70 > 60)
        PuntuacionesHU9HU10.verificarYRegistrarPuntuacion(70, scanner);

        // Verificar que el archivo se actualizó correctamente
        List<PuntuacionesHU9HU10.Puntuacion> mejores = cargarDesdeArchivo(archivoTemporal);
        assertEquals(3, mejores.size());
        assertEquals("NuevoJugador", mejores.get(2).getNombre());
        assertEquals(70, mejores.get(2).getPuntuacion());
    }

    @Test
    public void testMuestraMejoresPuntuaciones() {
        // Test para HU10: Mostrar la lista de mejores puntuaciones
        // Este test verifica que se muestren las puntuaciones correctamente y el mensaje de despedida

        // Preparar archivo temporal con puntuaciones
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {
            bw.write("Jugador1:100\n");
            bw.write("Jugador2:80\n");
        } catch (IOException e) {
            fail("Error al preparar archivo temporal");
        }

        // Capturar la salida de System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Llamar al método
            PuntuacionesHU9HU10.mostrarMejoresPuntuacionesYDespedida();

            // Obtener la salida capturada
            String output = outputStream.toString();

            // Verificar que contiene las puntuaciones y el mensaje de despedida
            assertTrue(output.contains("Mejores puntuaciones:"));
            assertTrue(output.contains("1. Jugador1 - 100 puntos"));
            assertTrue(output.contains("2. Jugador2 - 80 puntos"));
            assertTrue(output.contains("¡Gracias por jugar!"));
        } finally {
            // Restaurar System.out
            System.setOut(originalOut);
        }
    }

    // Método auxiliar para cargar puntuaciones desde archivo (similar al de la clase)
    private List<PuntuacionesHU9HU10.Puntuacion> cargarDesdeArchivo(File archivo) {
        List<PuntuacionesHU9HU10.Puntuacion> mejores = new ArrayList<>();
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(":");
                    if (partes.length == 2) {
                        String nombre = partes[0];
                        int puntuacion = Integer.parseInt(partes[1]);
                        mejores.add(new PuntuacionesHU9HU10.Puntuacion(nombre, puntuacion));
                    }
                }
            } catch (IOException e) {
                fail("Error al leer archivo temporal");
            }
        }
        Collections.sort(mejores);
        return mejores;
    }
}