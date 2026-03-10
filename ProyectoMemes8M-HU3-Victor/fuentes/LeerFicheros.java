import java.io.*;
import java.nio.file.*;
import javax.xml.parsers.*;

import netscape.javascript.JSObject;
import org.w3c.dom.*;
import java.util.*;
import org.json.*;

/**
 * Clase encargada de leer y parsear los ficheros de datos de la aplicación.
 * Gestiona la lectura de memes.txt, realidades.json y soluciones.xml.
 *
 * @author Alejandro, Victor, Javier
 * @version 1.0
 */
public class LeerFicheros {

    /** Ruta al fichero XML de soluciones. */
    String ruta = "../datos/soluciones.xlm";

    /** Ruta al fichero TXT de memes. */
    String ruta2 = "../datos/memes.txt";


    /**
     * Lee el fichero soluciones.xml y extrae la relación entre memes y realidades.
     *
     * @throws Exception si el fichero no existe o tiene un formato XML incorrecto
     */
    public void leerXML() throws Exception {
        File ficheroXML = new File(ruta);
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor = factoria.newDocumentBuilder();
        Document documento = constructor.parse(ficheroXML);

        Element raiz = documento.getDocumentElement();
        String realidad = raiz.getElementsByTagName("realidad").item(0).getTextContent();
        Integer id = Integer.valueOf(raiz.getElementsByTagName("id").item(0).getTextContent());
    }


    /**
     * Lee el fichero memes.txt y muestra su contenido por pantalla.
     *
     * @throws IOException si el fichero no existe o no puede ser leído
     */
    public void leerTXT() throws IOException {
        Path path = Paths.get(ruta2);
        if (!Files.exists(path)) {
            throw new IOException("El fichero '...' no existe.");
        }
        List<String> memes = Files.readAllLines(path);

        String nombreFichero = "memes.txt";
        try {
            List<String> lineas = leerFichero('memes.txt');
            System.out.println("Contenido del fichero:");
            for (String linea : lineas) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }


    /**
     * Lee el fichero realidades.json y genera una lista de objetos MemesRealidades.
     * Cada objeto contiene el id, nombre del meme, realidad, referencia y URL de la fuente.
     *
     * @return lista de objetos MemesRealidades con todos los datos cargados
     * @throws Exception si el fichero no existe o tiene un formato JSON incorrecto
     */
    public List<MemesRealidades> obtenerMemesPorJson() throws Exception {
        Path path = Paths.get("../datos/realidades.json");

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

            MemesRealidades meme = new MemesRealidades(id, name, reality, reference, url);
            listaMemes.add(meme);
        }

        return listaMemes;
    }
}