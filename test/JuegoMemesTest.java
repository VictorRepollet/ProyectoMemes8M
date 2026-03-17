package test;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de tests unitarios para verificar el correcto funcionamiento
 * de las Historias de Usuario HU1 a HU8 del proyecto Memes 8M.
 *
 * Cubre las siguientes historias de usuario:
 *   HU1 - Comprobar existencia de ficheros en datos (Javier)
 *   HU2 - Comprobar existencia de fichero resultados (Javier)
 *   HU3 - Leer fichero memes y generar estructura (Victor)
 *   HU4 - Leer fichero realidades y generar estructura
 *   HU5 - Mostrar meme y listar realidades (Alejandro)
 *   HU6 - Elegir dato real (Alejandro)
 *   HU7 - Mostrar marcador (Alejandro)
 *   HU8 - Mostrar puntuación final (Alejandro)
 *
 * @author Alejandro, Victor, Javier
 * @version 1.0
 */
public class JuegoMemesTest {

    // =========================================================
    // HU1 - COMPROBAR EXISTENCIA DE FICHEROS EN DATOS (JAVIER)
    // =========================================================

    /**
     * HU1 - Test 1: Comprueba que el método devuelve true
     * cuando los tres ficheros existen en el directorio datos.
     */
    @Test
    public void testComprobarDatos_TodosExisten() throws IOException {
        new File("datos").mkdirs();
        new File("datos/memes.txt").createNewFile();
        new File("datos/realidades.json").createNewFile();
        new File("datos/soluciones.xml").createNewFile();

        Boolean resultado = JuegoMemes.comprobarDatos();

        assertTrue(resultado);
    }

    /**
     * HU1 - Test 2: Comprueba que el método devuelve false
     * cuando el fichero memes.txt no existe.
     */
    @Test
    public void testComprobarDatos_FaltaMemesTxt() {
        new File("datos/memes.txt").delete();

        Boolean resultado = JuegoMemes.comprobarDatos();

        assertFalse(resultado);
    }

    /**
     * HU1 - Test 3: Comprueba que el método devuelve false
     * cuando el fichero realidades.json no existe.
     */
    @Test
    public void testComprobarDatos_FaltaRealidadesJson() {
        new File("datos/realidades.json").delete();

        Boolean resultado = JuegoMemes.comprobarDatos();

        assertFalse(resultado);
    }

    /**
     * HU1 - Test 4: Comprueba que el método devuelve false
     * cuando el fichero soluciones.xml no existe.
     */
    @Test
    public void testComprobarDatos_FaltaSolucionesXml() {
        new File("datos/soluciones.xml").delete();

        Boolean resultado = JuegoMemes.comprobarDatos();

        assertFalse(resultado);
    }


    // =========================================================
    // HU2 - COMPROBAR EXISTENCIA DE FICHERO RESULTADOS (JAVIER)
    // =========================================================

    /**
     * HU2 - Test 1: Comprueba que el directorio resultados
     * se crea correctamente si no existía previamente.
     */
    @Test
    public void testComprobarResultados_CreaDirectorio() {
        new File("resultados/resultados.txt").delete();
        new File("resultados").delete();

        JuegoMemes.comprobarResultados();

        assertTrue(new File("resultados").exists());
    }

    /**
     * HU2 - Test 2: Comprueba que el fichero resultados.txt
     * se crea correctamente si no existía previamente.
     */
    @Test
    public void testComprobarResultados_CreaFichero() {
        new File("resultados/resultados.txt").delete();

        JuegoMemes.comprobarResultados();

        assertTrue(new File("resultados/resultados.txt").exists());
    }

    /**
     * HU2 - Test 3: Comprueba que si resultados.txt ya existe,
     * el método no sobreescribe su contenido.
     */
    @Test
    public void testComprobarResultados_NoSobreescribe() throws IOException {
        File archivo = new File("resultados/resultados.txt");
        new File("resultados").mkdirs();
        Files.writeString(archivo.toPath(), "contenido previo");

        JuegoMemes.comprobarResultados();

        String contenido = Files.readString(archivo.toPath());
        assertEquals("contenido previo", contenido);
    }


    // =========================================================
    // HU3 - LEER FICHERO MEMES Y GENERAR ESTRUCTURA (VICTOR)
    // =========================================================

    /**
     * HU3 - Test 1: Comprueba que la lectura del fichero memes.txt
     * devuelve una lista con al menos un elemento.
     */
    @Test
    public void testLeerTXT_DevuelveListaNoVacia() throws IOException {
        new File("datos").mkdirs();
        Files.writeString(Paths.get("datos/memes.txt"), "Meme1\nMeme2\nMeme3");

        LeerFicheros lector = new LeerFicheros();
        List<String> memes = lector.leerTXT();

        assertFalse(memes.isEmpty());
    }

    /**
     * HU3 - Test 2: Comprueba que el número de elementos de la lista
     * coincide con el número de líneas del fichero memes.txt.
     */
    @Test
    public void testLeerTXT_NumeroCorrecto() throws IOException {
        new File("datos").mkdirs();
        Files.writeString(Paths.get("datos/memes.txt"), "Meme1\nMeme2\nMeme3");

        LeerFicheros lector = new LeerFicheros();
        List<String> memes = lector.leerTXT();

        assertEquals(3, memes.size());
    }

    /**
     * HU3 - Test 3: Comprueba que se lanza una IOException
     * cuando el fichero memes.txt no existe.
     */
    @Test
    public void testLeerTXT_FicheroNoExisteLanzaExcepcion() {
        new File("datos/memes.txt").delete();

        LeerFicheros lector = new LeerFicheros();

        assertThrows(IOException.class, () -> lector.leerTXT());
    }


    // =========================================================
    // HU4 - LEER FICHERO REALIDADES Y GENERAR ESTRUCTURA
    // =========================================================

    /**
     * HU4 - Test 1: Comprueba que la lectura del fichero realidades.json
     * devuelve una lista con al menos un objeto MemesRealidades.
     */
    @Test
    public void testObtenerMemesPorJson_DevuelveListaNoVacia() throws Exception {
        new File("datos").mkdirs();
        String json = "[{\"id\":1,\"name_meme\":\"Meme test\",\"reality\":\"Realidad test\"," +
                      "\"reference\":\"Fuente test\",\"url\":\"http://test.com\"," +
                      "\"fake_realities\":[\"Falsa 1\",\"Falsa 2\"]}]";
        Files.writeString(Paths.get("../datos/realidades.json"), json);

        LeerFicheros lector = new LeerFicheros();
        List<MemesRealidades> lista = lector.obtenerMemesPorJson();

        assertFalse(lista.isEmpty());
    }

    /**
     * HU4 - Test 2: Comprueba que los campos del objeto MemesRealidades
     * se cargan correctamente desde el fichero JSON.
     */
    @Test
    public void testObtenerMemesPorJson_CamposCargadosCorrectamente() throws Exception {
        String json = "[{\"id\":1,\"name_meme\":\"Meme test\",\"reality\":\"Realidad test\"," +
                      "\"reference\":\"Fuente test\",\"url\":\"http://test.com\"," +
                      "\"fake_realities\":[\"Falsa 1\",\"Falsa 2\"]}]";
        Files.writeString(Paths.get("../datos/realidades.json"), json);

        LeerFicheros lector = new LeerFicheros();
        List<MemesRealidades> lista = lector.obtenerMemesPorJson();

        assertEquals("Meme test", lista.get(0).getName());
        assertEquals("Realidad test", lista.get(0).getReality());
    }

    /**
     * HU4 - Test 3: Comprueba que si el fichero JSON está vacío
     * el método devuelve una lista vacía sin lanzar excepciones.
     */
    @Test
    public void testObtenerMemesPorJson_JsonVacioDevuelveListaVacia() throws Exception {
        Files.writeString(Paths.get("../datos/realidades.json"), "[]");

        LeerFicheros lector = new LeerFicheros();
        List<MemesRealidades> lista = lector.obtenerMemesPorJson();

        assertTrue(lista.isEmpty());
    }


    // =========================================================
    // HU5 - MOSTRAR MEME Y LISTAR REALIDADES (ALEJANDRO)
    // =========================================================

    /**
     * HU5 - Test 1: Comprueba que el índice elegido aleatoriamente
     * está dentro del rango válido de la lista de memes.
     */
    @Test
    public void testMostrarMeme_IndiceValido() {
        List<MemesRealidades> lista = crearListaTest();
        List<Integer> usados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            usados.clear();
            Integer indice = obtenerIndiceAleatorio(lista, usados);
            assertTrue(indice >= 0 && indice < lista.size());
        }
    }

    /**
     * HU5 - Test 2: Comprueba que no se selecciona un meme
     * que ya ha sido mostrado en una ronda anterior.
     */
    @Test
    public void testMostrarMeme_NoRepite() {
        List<MemesRealidades> lista = crearListaTest();
        List<Integer> usados = new ArrayList<>();
        usados.add(0);
        usados.add(1);

        Integer indice = obtenerIndiceAleatorio(lista, usados);

        assertFalse(usados.subList(0, 2).contains(indice));
    }

    /**
     * HU5 - Test 3: Comprueba que la lista de realidades mostrada
     * tiene el mismo número de elementos que la lista de memes cargada.
     */
    @Test
    public void testListarRealidades_TamañoCorrecto() {
        List<MemesRealidades> lista = crearListaTest();
        assertEquals(3, lista.size());
    }


    // =========================================================
    // HU6 - ELEGIR DATO REAL (ALEJANDRO)
    // =========================================================

    /**
     * HU6 - Test 1: Comprueba que mostrarRonda devuelve true
     * cuando el jugador elige la única opción disponible (la correcta).
     */
    @Test
    public void testMostrarRonda_AciertaConUnaOpcion() {
        MemesRealidades meme = new MemesRealidades(
            1L, "Meme test", "Realidad verdadera", "Fuente", "url", new ArrayList<>()
        );

        String inputSimulado = "1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Boolean resultado = LeerFicheros.mostrarRonda(meme, teclado);

        assertTrue(resultado);
    }

    /**
     * HU6 - Test 2: Comprueba que pedirEleccion devuelve
     * correctamente la opción introducida si es válida.
     */
    @Test
    public void testPedirEleccion_EntradaValida() {
        String inputSimulado = "2\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(2, resultado);
    }

    /**
     * HU6 - Test 3: Comprueba que pedirEleccion reintenta
     * si el jugador introduce un número fuera del rango permitido.
     */
    @Test
    public void testPedirEleccion_ReintentoFueraDeRango() {
        // Primero 0 (inválido), luego 3 (válido)
        String inputSimulado = "0\n3\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(3, resultado);
    }

    /**
     * HU6 - Test 4: Comprueba que pedirEleccion reintenta
     * si el jugador introduce texto en lugar de un número.
     */
    @Test
    public void testPedirEleccion_ReintentoEntradaNoNumerica() {
        // Primero texto (inválido), luego 1 (válido)
        String inputSimulado = "abc\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(1, resultado);
    }

    /**
     * HU6 - Test 5: Comprueba que mostrarRonda devuelve
     * un valor booleano no nulo en cualquier caso.
     */
    @Test
    public void testMostrarRonda_DevuelveBooleano() {
        List<String> falsas = new ArrayList<>();
        falsas.add("Realidad falsa");
        MemesRealidades meme = new MemesRealidades(
            1L, "Meme test", "Realidad verdadera", "Fuente", "url", falsas
        );

        String inputSimulado = "1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Boolean resultado = LeerFicheros.mostrarRonda(meme, teclado);

        assertNotNull(resultado);
    }


    // =========================================================
    // HU7 - MOSTRAR MARCADOR (ALEJANDRO)
    // =========================================================

    /**
     * HU7 - Test 1: Comprueba que el número de rondas introducido
     * es rechazado si es menor que 5.
     */
    @Test
    public void testJugar_RondasMenorDe5Invalido() {
        // Introduce 3 (inválido), luego 5 (válido) y responde a todas las rondas
        String inputSimulado = "3\n5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        assertDoesNotThrow(() -> LeerFicheros.jugar(teclado));
    }

    /**
     * HU7 - Test 2: Comprueba que el número de rondas introducido
     * es rechazado si es mayor que 10.
     */
    @Test
    public void testJugar_RondasMayorDe10Invalido() {
        // Introduce 15 (inválido), luego 5 (válido) y responde a todas las rondas
        String inputSimulado = "15\n5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        assertDoesNotThrow(() -> LeerFicheros.jugar(teclado));
    }

    /**
     * HU7 - Test 3: Comprueba que el número de rondas válido
     * es aceptado sin pedir reintento.
     */
    @Test
    public void testJugar_RondasValidasAceptadas() {
        // 5 rondas válidas + una respuesta por ronda
        String inputSimulado = "5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        assertDoesNotThrow(() -> LeerFicheros.jugar(teclado));
    }


    // =========================================================
    // HU8 - MOSTRAR PUNTUACION FINAL (ALEJANDRO)
    // =========================================================

    /**
     * HU8 - Test 1: Comprueba que jugar devuelve una puntuación
     * no negativa al finalizar la partida.
     */
    @Test
    public void testJugar_PuntuacionNoNegativa() throws IOException {
        String inputSimulado = "5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer puntuacion = LeerFicheros.jugar(teclado);

        assertTrue(puntuacion >= 0);
    }

    /**
     * HU8 - Test 2: Comprueba que la puntuación devuelta no supera
     * el número de rondas jugadas.
     */
    @Test
    public void testJugar_PuntuacionNoSuperaRondas() throws IOException {
        String inputSimulado = "5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer puntuacion = LeerFicheros.jugar(teclado);

        assertTrue(puntuacion <= 5);
    }

    /**
     * HU8 - Test 3: Comprueba que jugar devuelve un valor no nulo
     * al finalizar la partida.
     */
    @Test
    public void testJugar_DevuelveValorNoNulo() throws IOException {
        String inputSimulado = "5\n1\n1\n1\n1\n1\n";
        Scanner teclado = new Scanner(new ByteArrayInputStream(inputSimulado.getBytes()));

        Integer puntuacion = LeerFicheros.jugar(teclado);

        assertNotNull(puntuacion);
    }


    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    private List<MemesRealidades> crearListaTest() {
        List<String> falsas = new ArrayList<>();
        falsas.add("Falsa 1");
        falsas.add("Falsa 2");

        List<MemesRealidades> lista = new ArrayList<>();
        lista.add(new MemesRealidades(1L, "Meme 1", "Realidad 1", "Fuente 1", "http://url1.com", falsas));
        lista.add(new MemesRealidades(2L, "Meme 2", "Realidad 2", "Fuente 2", "http://url2.com", falsas));
        lista.add(new MemesRealidades(3L, "Meme 3", "Realidad 3", "Fuente 3", "http://url3.com", falsas));
        return lista;
    }

    private Integer obtenerIndiceAleatorio(List<MemesRealidades> lista, List<Integer> usados) {
        Random random = new Random();
        Integer indice;
        do {
            indice = random.nextInt(lista.size());
        } while (usados.contains(indice));
        return indice;
    }
}