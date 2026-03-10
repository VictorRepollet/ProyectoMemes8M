import java.io.*;
import java.nio.file.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import java.util.*;
import org.json.*;

public class LeerFicheros {
    public static Map<Long, MemesRealidades> crearEstructuraDeSoluciones() throws Exception {
        String ruta = "../datos/soluciones.xml";
        File ficheroXML = new File(ruta);

        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance(); //Constructores de documentos
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

            mapaDeMemes.put(id,memeCreadoConXml);

        }
        return mapaDeMemes;

    }

    public static List<String> leerFichero(String ruta) throws Exception {
        if (ruta == null){
            throw new NullPointerException("La ruta no puede ser nula.");
        }
        if (ruta.isEmpty() || ruta.equals(" ")){
            throw new Exception("No se ha introducido ninguna ruta.");
        }
        Path path = Paths.get(ruta);

        List<String> contenido = Files.readAllLines(path);
        if (contenido.isEmpty()){
            throw new Exception("El fichero esta vacío.");
        }
        return contenido;

    }

    public static void escribirPuntuaciones() throws IOException {
        Scanner sc = new Scanner(System.in);
        String ruta = "../resultados/resultados.txt";
        Path path = Paths.get(ruta);

        System.out.println("Introduce tu nombre y quedara registrado con tu puntuacion.");

        String nombre = sc.nextLine();
        String textoCompeto = nombre + " - "; //Obtener puntuacion actual, por programar

        sc.close();

        Files.write(path, textoCompeto.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        // Falta comprobar la puntuacion y la posicion del intento/persona
        // Posibilidad de usar metodo leerFichero con la ruta de resultado
    }

    public static List<MemesRealidades> obtenerMemesPorJson() throws IOException {
        String ruta = "../datos/realidades.json";
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

    public static void main(String[] args) throws Exception {
        List<MemesRealidades> listaMemes = obtenerMemesPorJson();
        System.out.println(listaMemes);

        List<String> lista = leerFichero("../datos/memes.txt");
        System.out.println(lista);

        Map<Long,MemesRealidades> memesPorxml = crearEstructuraDeSoluciones();

        System.out.println(memesPorxml);
    }
}



