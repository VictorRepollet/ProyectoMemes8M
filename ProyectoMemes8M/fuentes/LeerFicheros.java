import java.io.*;
import java.nio.file.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import java.util.*;
import org.json.*;

/**
 * Clase encargada de la lectura y escritura de ficheros
 * necesarios para el funcionamiento de la aplicación Memes 8M.
 *
 * Proporciona métodos estáticos para:
 * <ul>
 *     <li>Leer el fichero de memes en formato TXT</li>
 *     <li>Leer el fichero de realidades en formato JSON</li>
 *     <li>Leer el fichero de soluciones en formato XML</li>
 *     <li>Escribir los resultados de las partidas</li>
 * </ul>
 *
 * @author Victor, Alejandro, Javier
 * @version 1.0
 */
public class LeerFicheros {

    /**
     * Lee el fichero {@code soluciones.xml} y construye un mapa de memes
     * indexado por su identificador numérico.
     *
     * Cada entrada del mapa asocia un {@code id} con un objeto
     * {@link MemesRealidades} que contiene el nombre del meme y su realidad.
     *
     * @return {@link Map} con los memes del fichero XML, indexados por su {@code id}
     * @throws Exception si el fichero no existe, no se puede leer o tiene un formato incorrecto
     */
    public static Map<Long, MemesRealidades> crearEstructuraDeSoluciones() throws Exception {
        String ruta = "../datos/soluciones.xml";
        File ficheroXML = new File(ruta);

        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance(); // Constructores de documentos
        DocumentBuilder constructor = factoria.newDocumentBuilder();
        Document documento = constructor.parse(ficheroXML);

        Element raiz = documento.getDocumentElement();
        NodeList nodosTotalesDeMemes = raiz.getElementsByTagName("meme");
        Map<Long, MemesRealidades> mapaDeMemes = new HashMap<>();

        for (int i = 0; i < nodosTotalesDeMemes.getLength(); i++) {
            Element meme = (Element) nodosTotalesDeMemes.item(i);

            Long id = Long.valueOf(meme.getElementsByTagName("id").item(0).getTextContent());
            String nombre = meme.getElementsByTagName("nombre").item(0).getTextContent();
            String realidad = meme.getElementsByTagName("realidad").item(0).getTextContent();

            MemesRealidades memeCreadoConXml = new MemesRealidades(nombre, realidad);

            mapaDeMemes.put(id, memeCreadoConXml);
        }
        return mapaDeMemes;
    }

    /**
     * Lee todas las líneas de un fichero de texto dado su ruta.
     *
     * Realiza validaciones previas sobre la ruta proporcionada y lanza
     * excepciones descriptivas si la ruta es nula, vacía o si el fichero
     * no contiene ninguna línea.
     *
     * @param ruta ruta del fichero a leer
     * @return {@link List} de cadenas con el contenido del fichero, una línea por elemento
     * @throws NullPointerException si la ruta proporcionada es {@code null}
     * @throws Exception si la ruta está vacía, o si el fichero no existe o está vacío
     */
    public static List<String> leerFichero(String ruta) throws Exception {
        if (ruta == null) {
            throw new NullPointerException("La ruta no puede ser nula.");
        }
        if (ruta.isEmpty() || ruta.equals(" ")) {
            throw new Exception("No se ha introducido ninguna ruta.");
        }

        Path path = Paths.get(ruta);
        List<String> contenido = Files.readAllLines(path);

        if (contenido.isEmpty()) {
            throw new Exception("El fichero esta vacío.");
        }
        return contenido;
    }

    /**
     * Solicita el nombre del jugador por consola y guarda su puntuación
     * en el fichero {@code resultados.txt}, manteniendo un ranking por posición.
     *
     * La posición se calcula automáticamente en función del número de entradas
     * ya existentes en el fichero. El formato de cada línea es:
     * <pre>
     *     1. NombreJugador - Puntuacion
     * </pre>
     *
     * @param puntuacion puntuación obtenida por el jugador al finalizar la partida
     * @param teclado    {@link Scanner} compartido para leer la entrada del usuario
     * @throws Exception si ocurre un error al leer o escribir el fichero de resultados
     */
    public static void escribirPuntuaciones(Integer puntuacion, Scanner teclado) throws Exception {
        String ruta = "../resultados/resultados.txt";
        Path path = Paths.get(ruta);

        System.out.println("Introduce tu nombre y quedara registrado con tu puntuacion.");
        String nombre = teclado.nextLine();

        // Leer líneas existentes para calcular la posición
        List<String> lineasExistentes = new ArrayList<>();
        if (Files.exists(path)) {
            lineasExistentes = new ArrayList<>(Files.readAllLines(path));
        }

        // La posición es el número de entradas existentes + 1
        Integer posicion = lineasExistentes.size() + 1;

        String nuevaLinea = posicion + ". " + nombre + " - " + puntuacion;

        // Añadir la nueva línea a la lista y reescribir el fichero completo
        lineasExistentes.add(nuevaLinea);
        Files.write(path, lineasExistentes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Puntuacion guardada en posicion " + posicion + ": " + nuevaLinea);
    }

    /**
     * Lee el fichero {@code realidades.json} y construye una lista de objetos
     * {@link MemesRealidades} con todos los datos de cada meme.
     *
     * Cada objeto incluye el identificador, nombre, realidad, referencia,
     * URL de imagen y una lista de realidades falsas asociadas al meme.
     *
     * @return {@link List} de {@link MemesRealidades} con los datos del fichero JSON
     * @throws IOException si el fichero no existe o no se puede leer
     */
    public static List<MemesRealidades> obtenerMemesPorJson() throws IOException {
        String ruta = "datos/realidades.json";
        Path path = Paths.get(ruta);

        String contenido = new String(Files.readAllBytes(path));
        List<MemesRealidades> listaMemes = new ArrayList<>();

        JSONArray arrayJson = new JSONArray(contenido);

        for (int i = 0; i < arrayJson.length(); i++) {
            JSONObject objetoJSON = arrayJson.getJSONObject(i);

            Long id = objetoJSON.getLong("id");
            String name = objetoJSON.getString("name_meme");
            String reality = objetoJSON.getString("reality");
            String reference = objetoJSON.getString("reference");
            String url = objetoJSON.getString("url");

            JSONArray fakeArray = objetoJSON.getJSONArray("fake_realities");
            List<String> fakeRealities = new ArrayList<>();

            for (int j = 0; j < fakeArray.length(); j++) {
                fakeRealities.add(fakeArray.getString(j));
            }

            MemesRealidades meme = new MemesRealidades(id, name, reality, reference, url, fakeRealities);
            listaMemes.add(meme);
        }

        return listaMemes;
    }
}