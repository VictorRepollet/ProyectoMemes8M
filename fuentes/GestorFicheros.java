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
public class GestorFicheros {

    /**
     * Lee el fichero {@code soluciones.xml} y construye un mapa de memes
     * indexado por su identificador numérico.
     *
     * @return {@link Map} con los memes del fichero XML, indexados por su {@code id}
     * @throws Exception si el fichero no existe, no se puede leer o tiene un formato incorrecto
     */
    public static Map<Long, MemesRealidades> crearEstructuraDeSoluciones() throws Exception {
        String ruta = "datos/soluciones.xml";
        File ficheroXML = new File(ruta);

        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
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
     * @param ruta ruta del fichero a leer
     * @return {@link List} de cadenas con el contenido del fichero
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
     * @param puntuacion puntuación obtenida por el jugador al finalizar la partida
     * @param teclado    {@link Scanner} compartido para leer la entrada del usuario
     * @throws Exception si ocurre un error al leer o escribir el fichero de resultados
     */
    public static void escribirPuntuaciones(Integer puntuacion, Scanner teclado) throws Exception {
        String ruta = "resultados/resultados.txt";
        Path path = Paths.get(ruta);

        System.out.println("Introduce tu nombre y quedara registrado con tu puntuacion.");
        String nombre = teclado.nextLine();

        List<String> lineasExistentes = new ArrayList<>();
        if (Files.exists(path)) {
            lineasExistentes = new ArrayList<>(Files.readAllLines(path));
        }

        Integer posicion = lineasExistentes.size() + 1;
        String nuevaLinea = posicion + ". " + nombre + " - " + puntuacion;

        lineasExistentes.add(nuevaLinea);
        Files.write(path, lineasExistentes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Puntuacion guardada en posicion " + posicion + ": " + nuevaLinea);
    }

    /**
     * Lee el fichero {@code realidades.json} y construye una lista de objetos
     * {@link MemesRealidades} con todos los datos de cada meme.
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

    /**
     * HU6 + HU7 + HU8 - Ejecuta todas las rondas del juego, muestra el marcador
     * tras cada ronda y la puntuación final al terminar.
     *
     * @param teclado Scanner compartido
     * @return puntuación total obtenida
     * @throws IOException si hay error al leer el fichero JSON
     */
    public static Integer jugar(Scanner teclado) throws IOException {

        List<MemesRealidades> memes = obtenerMemesPorJson();
        Collections.shuffle(memes);

        Integer puntuacion = 0;

        // HU7 - Pedir número de rondas
        System.out.println("¿Cuantas rondas deseas jugar? Introduce un numero entre 5 y 10.");
        Integer rondasAJugar = Integer.valueOf(teclado.nextLine());

        while (rondasAJugar < 5 || rondasAJugar > 10) {
            System.out.println("Número de rondas invalido, introduce un valor entre 5 y 10.");
            rondasAJugar = Integer.valueOf(teclado.nextLine());
        }

        Integer ronda = 1;

        for (int i = 0; i < rondasAJugar; i++) {

            MemesRealidades meme = memes.get(i);

            System.out.println("\n--- Ronda " + ronda + " de " + rondasAJugar + " ---");

            Boolean acerto = mostrarRonda(meme, teclado);

            if (acerto) {
                puntuacion++;
                System.out.println("¡Correcto!");
            } else {
                System.out.println("Incorrecto. La respuesta era: " + meme.getReality());
            }

            // HU7 - Mostrar marcador tras cada ronda
            System.out.println("--------------------------------");
            System.out.println("  MARCADOR: " + puntuacion + " / " + ronda);
            System.out.println("--------------------------------");

            ronda++;
        }

        // HU8 - Mostrar puntuación final
        System.out.println("\n================================");
        System.out.println("        PUNTUACIÓN FINAL");
        System.out.println("================================");
        System.out.println("  Acertaste " + puntuacion + " de " + rondasAJugar + " preguntas.");
        System.out.println("================================\n");

        return puntuacion;
    }

    /**
     * HU6 - Muestra una ronda: el meme, las opciones mezcladas y recoge la respuesta.
     *
     * @param meme    meme de la ronda actual
     * @param teclado Scanner compartido
     * @return true si el jugador acertó, false si no
     */
    public static boolean mostrarRonda(MemesRealidades meme, Scanner teclado) {
        List<String> opciones = new ArrayList<>();
        opciones.add(meme.getReality());
        opciones.addAll(meme.getFakeRealities());
        Collections.shuffle(opciones);

        Integer indiceCorrecta = opciones.indexOf(meme.getReality()) + 1;

        System.out.println("\nMEME: " + meme.getName());
        System.out.println("¿Cuál es la realidad verdadera?\n");
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + opciones.get(i));
        }

        Integer eleccion = pedirEleccion(teclado, opciones.size());
        return eleccion == indiceCorrecta;
    }

    /**
     * HU6 - Solicita una opción válida al jugador, repitiendo si la entrada es incorrecta.
     *
     * @param teclado     Scanner compartido
     * @param numOpciones número total de opciones disponibles
     * @return número elegido por el jugador (entre 1 y numOpciones)
     */
    public static Integer pedirEleccion(Scanner teclado, int numOpciones) {
        Integer eleccion = -1;

        while (eleccion < 1 || eleccion > numOpciones) {
            System.out.print("\nElige una opción (1-" + numOpciones + "): ");
            try {
                eleccion = Integer.parseInt(teclado.nextLine().trim());
                if (eleccion < 1 || eleccion > numOpciones) {
                    System.out.println("Opción no válida. Introduce un número entre 1 y " + numOpciones + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número.");
            }
        }

        return eleccion;
    }
}