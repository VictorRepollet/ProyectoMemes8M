import java.io.*;      
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class LeerFicheros {
    public static void main(String[] args) throws Exception {

        String ruta = "../datos/soluciones.xlm";
        String ruta2 = "../datos/memes.txt"


        public void leerXML(String ruta) {
            File ficheroXML = new File(ruta);
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance(); //Constructores de documentos
            DocumentBuilder constructor = factoria.newDocumentBuilder(); 
            Document documento = constructor.parse(ficheroXML);

            Element raiz = documento.getDocumentElement();
            String realidad = raiz.getElementsByTagName("realidad").item(0).getTextContent();
            Integer id = Integer.valueOf(raiz.getElementsByTagName("id").item(0).getTextContent());

           MMemesRealidades memes = new MemesRealidades(realidad, id);
            System.out.println(memes);
        }          
    }

    public void leerTXT (String ruta){
     public static List<String> leerFichero(String nombreFichero) throws IOException {
        Path ruta = Paths.get(memes.txt);
        if (!Files.exists(../datos/memes.txt)) {
            throw new IOException("El fichero '" + nombreFichero + "' no existe.");
        }
        return Files.readAllLines(ruta2);
    }
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
    
}