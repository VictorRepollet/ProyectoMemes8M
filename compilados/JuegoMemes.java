import java.io.File;
import java.io.IOException;

public class JuegoMemes {

    public static void main(String[] args) {

        System.out.println("Iniciando comprobación de archivos...\n");

        boolean datosOK = comprobarDatos();

        if(!datosOK){
            System.out.println("\nFaltan archivos necesarios.");
            System.out.println("El programa se detiene.");
            return;
        }

        comprobarResultados();

        System.out.println("\nTodos los archivos necesarios existen.");
        System.out.println("El sistema puede iniciar correctamente.");
    }


    // HU1: comprobar archivos del directorio datos
    public static boolean comprobarDatos(){

        boolean todoExiste = true;

        File memes = new File("datos/memes.txt");
        File realidades = new File("datos/realidades.json");
        File soluciones = new File("datos/soluciones.xml");

        if(memes.exists()){
            System.out.println("✔ memes.txt encontrado");
        } else {
            System.out.println("✘ memes.txt NO encontrado");
            todoExiste = false;
        }

        if(realidades.exists()){
            System.out.println("✔ realidades.json encontrado");
        } else {
            System.out.println("✘ realidades.json NO encontrado");
            todoExiste = false;
        }

        if(soluciones.exists()){
            System.out.println("✔ soluciones.xml encontrado");
        } else {
            System.out.println("✘ soluciones.xml NO encontrado");
            todoExiste = false;
        }

        return todoExiste;
    }


    // HU2: comprobar o crear resultados.txt
    public static void comprobarResultados(){

        File carpeta = new File("resultados");

        if(!carpeta.exists()){
            System.out.println("\nCarpeta resultados no existe. Creándola...");
            carpeta.mkdir();
        }

        File archivo = new File("resultados/resultados.txt");

        if(archivo.exists()){
            System.out.println("✔ resultados.txt ya existe");
        } else {

            try{
                archivo.createNewFile();
                System.out.println("✔ resultados.txt creado correctamente");
            }
            catch(IOException e){
                System.out.println("Error al crear resultados.txt");
            }

        }

    }
}