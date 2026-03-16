import java.util.*;

/**
 * Clase que representa un Meme junto con su realidad asociada.
 *
 * Esta clase hereda de la clase Memes y añade información adicional
 * como una realidad verdadera, una referencia, una URL y una lista
 * de posibles realidades falsas.
 */
public class MemesRealidades extends Memes {

    private List<String> fakeRealities;
    private Long id;
    private String reality;
    private String reference;
    private String url;

    /**
     * Constructor completo de la clase MemesRealidades.
     *
     * Inicializa todos los atributos del objeto, incluyendo
     * el nombre del meme heredado de la clase Memes.
     *
     * @param id identificador del meme
     * @param name nombre del meme
     * @param reality realidad verdadera asociada
     * @param reference referencia o fuente de la información
     * @param url enlace relacionado con la información
     * @param fakeRealities lista de posibles realidades falsas
     */
    public MemesRealidades(Long id,String name, String reality, String reference, String url, List<String> fakeRealities){
        super(name);

        this.setId(id);
        this.setReality(reality);
        this.setReference(reference);
        this.setUrl(url);
        this.fakeRealities = fakeRealities;
    }

    /**
     * Constructor simplificado de la clase MemesRealidades.
     *
     * Permite crear un objeto solo con el nombre del meme
     * y su realidad verdadera.
     *
     * @param name nombre del meme
     * @param reality realidad verdadera asociada
     */
    public MemesRealidades(String name, String reality) {
        super(name);
        this.setReality(reality);
    }

    /**
     * Obtiene el identificador del meme.
     *
     * @return id del meme
     */
    public Long getId(){return this.id;}

    /**
     * Obtiene la realidad verdadera del meme.
     *
     * @return realidad verdadera
     */
    public String getReality(){return this.reality;}

    /**
     * Obtiene la referencia o fuente de la información.
     *
     * @return referencia
     */
    public String getReference(){return this.reference;}

    /**
     * Obtiene la URL asociada a la referencia.
     *
     * @return url relacionada
     */
    public String getUrl(){return this.url;}

    /**
     * Obtiene la lista de posibles realidades falsas.
     *
     * @return lista de realidades falsas
     */
    public List<String> getFakeRealities() {return this.fakeRealities;}

    /**
     * Establece el identificador del meme.
     *
     * @param id identificador del meme
     */
    private void setId(Long id){this.id = id;}

    /**
     * Establece la realidad verdadera del meme.
     *
     * @param reality realidad verdadera
     */
    private void setReality(String reality){this.reality = reality;}

    /**
     * Establece la referencia o fuente de la realidad.
     *
     * @param reference referencia de la información
     */
    private void setReference(String reference){this.reference = reference;}

    /**
     * Establece la URL asociada a la referencia.
     *
     * @param url enlace relacionado
     */
    private void setUrl(String url){this.url = url;}

}