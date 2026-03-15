/**
 * Clase que representa un Meme dentro de la aplicación.
 *
 * Cada objeto de esta clase almacena el nombre de un meme.
 * Se utiliza para gestionar y mostrar memes cargados desde los
 * ficheros de datos del programa.
 */
public class Memes{
    private String name;

    /**
     * Constructor de la clase Memes.
     *
     * Inicializa un meme asignándole un nombre.
     *
     * @param name nombre del meme
     */
    public Memes(String name){
        this.setName(name);
    };

    /**
     * Método privado que establece el nombre del meme.
     *
     * Se usa internamente para asignar el valor al atributo name.
     *
     * @param name nombre del meme
     */
    private void setName(String name){
        this.name = name;
    }

    /**
     * Obtiene el nombre del meme.
     *
     * @return nombre del meme
     */
    public String getName(){
        return this.name;
    }

    /**
     * Devuelve una representación en texto del objeto Meme.
     *
     * @return cadena con el nombre del meme formateado
     */
    public String toString(){
        return "Meme: " + this.getName();
    }

}