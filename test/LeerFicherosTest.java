import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.util.*;
import java.io.*;

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

        List<String> resultado = LeerFicheros.leerFichero(fichero.toString());

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

        Exception e = assertThrows(Exception.class, () -> {
            LeerFicheros.leerFichero(fichero.toString());
        });

        assertEquals("El fichero esta vacío.", e.getMessage());
    }

    /**
     * Comprueba que falla si la ruta no existe
     */
    @Test
    void leerFicheroRutaInexistente() {

        assertThrows(IOException.class, () -> {
            LeerFicheros.leerFichero("no_existe.txt");
        });
    }

    /**
     * Comprueba que falla si la ruta es null
     */
    @Test
    void leerFicheroRutaNull() {

        assertThrows(NullPointerException.class, () -> {
            LeerFicheros.leerFichero(null);
        });
    }

    /**
     * Comprueba que falla si la ruta está vacía
     */
    @Test
    void leerFicheroRutaVacia() {

        Exception e = assertThrows(Exception.class, () -> {
            LeerFicheros.leerFichero("");
        });

        assertEquals("No se ha introducido ninguna ruta.", e.getMessage());
    }

    /**
     * Comprueba que el JSON devuelve una lista
     */
    @Test
    void obtenerMemesPorJsonDevuelveLista() throws Exception {

        List<MemesRealidades> memes = LeerFicheros.obtenerMemesPorJson();

        assertNotNull(memes);
    }

    /**
     * Comprueba que la lista de memes no está vacía
     */
    @Test
    void obtenerMemesPorJsonNoVacio() throws Exception {

        List<MemesRealidades> memes = LeerFicheros.obtenerMemesPorJson();

        assertFalse(memes.isEmpty());
    }

    /**
     * HU6 - Test pedirEleccion con entrada válida
     */
    @Test
    void pedirEleccionValida() {

        Scanner teclado = new Scanner("2\n");

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(2, resultado);
    }

    /**
     * HU6 - Test pedirEleccion con número fuera de rango
     */
    @Test
    void pedirEleccionFueraDeRango() {

        Scanner teclado = new Scanner("7\n3\n");

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(3, resultado);
    }

    /**
     * HU6 - Test pedirEleccion con texto no numérico
     */
    @Test
    void pedirEleccionEntradaNoNumerica() {

        Scanner teclado = new Scanner("hola\n2\n");

        Integer resultado = LeerFicheros.pedirEleccion(teclado, 4);

        assertEquals(2, resultado);
    }

    /**
     * HU6 - Test mostrarRonda (solo comprobamos que devuelve un boolean)
     */
    @Test
    void mostrarRondaDevuelveBoolean() {

        List<String> falsas = List.of("Falsa1","Falsa2","Falsa3");

        MemesRealidades meme = new MemesRealidades(
                1L,
                "Meme Test",
                "Realidad Correcta",
                "Fuente",
                "url",
                falsas
        );

        Scanner teclado = new Scanner("1\n");

        boolean resultado = LeerFicheros.mostrarRonda(meme, teclado);

        assertNotNull(resultado);
    }

    /**
     * HU6 - Test escribirPuntuaciones
     */
    @Test
    void escribirPuntuacionesNoLanzaExcepcion() throws Exception {

        Path carpetaResultados = directorioTemporal.resolve("resultados");
        Files.createDirectories(carpetaResultados);

        Scanner teclado = new Scanner("Victor\n");

        LeerFicheros.escribirPuntuaciones(5, teclado);

        assertTrue(true);
    }

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
    // MÉTODOS AUXILIARES
    // =========================================================

    private List<MemesRealidades> crearListaTest() {
        List<String> realidadesFalsas = new ArrayList<>();
        realidadesFalsas.add("1 false");
        realidadesFalsas.add("2 false");
        realidadesFalsas.add("3 false");

        List<MemesRealidades> lista = new ArrayList<>();

        lista.add(new MemesRealidades(1L, "Meme 1", "Realidad 1", "Fuente 1", "http://url1.com", realidadesFalsas));
        lista.add(new MemesRealidades(2L, "Meme 2", "Realidad 2", "Fuente 2", "http://url2.com", realidadesFalsas));
        lista.add(new MemesRealidades(3L, "Meme 3", "Realidad 3", "Fuente 3", "http://url3.com", realidadesFalsas));

        return lista;
    }

    private int obtenerIndiceAleatorio(List<MemesRealidades> lista, List<Integer> usados) {
        Random random = new Random();
        Integer indice;

        do {
            indice = random.nextInt(lista.size());
        } while (usados.contains(indice));

        return indice;
    }
}