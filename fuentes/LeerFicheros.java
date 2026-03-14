import java.io.*;
import java.nio.file.*;

import java.util.*;
import org.json.*;

/**
 * Clase encargada de leer los distintos ficheros de datos utilizados
 * por la aplicación y transformar su contenido en estructuras de datos
 * manejables dentro del programa.
 *
 * Permite trabajar con ficheros XML, JSON y de texto.
 */
public class LeerFicheros {


    /**
     * Lee el contenido de un fichero de texto y devuelve todas sus líneas.
     *
     * @param ruta ruta del fichero que se desea leer
     * @return lista con todas las líneas del fichero
     * @throws Exception si la ruta es inválida o el fichero está vacío
     */
    public List<String> leerFichero(String ruta) throws Exception {
       Path path = Paths.get(ruta);

        List<String> contenido = Files.readAllLines(path);
        if (contenido.isEmpty()){
            throw new Exception("El fichero esta vacío.");
        }

        return contenido;
    }


    /**
     * Lee el fichero JSON que contiene información sobre memes
     * y genera una lista de objetos MemesRealidades.
     *
     * @return lista de memes con sus realidades y datos asociados
     * @throws IOException si ocurre un error al leer el fichero JSON
     */
    public List<MemesRealidades> obtenerMemesPorJson() throws IOException {
        String ruta = "../datos/realidades.json";
        Path path = Paths.get(ruta);

        String contenido = new String(Files.readAllBytes(path)).trim();
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