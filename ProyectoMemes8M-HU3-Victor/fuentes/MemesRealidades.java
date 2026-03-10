import java.util.*;

public class MemesRealidades extends Memes {

    private Long id;
    private String reality;
    private String reference;
    private String url;

    public MemesRealidades(Long id,String name, String reality, String reference, String url){
        super(name);

        this.setId(id);
        this.setReality(reality);
        this.setReference(reference);
        this.setUrl(url);
    }

    public Long getId(){return this.id;}

    public String getReality(){return this.reality;}

    public String getReference(){return this.reference;}

    public String getUrl(){return this.url;}

    private void setId(Long id){this.id = id;}

    private void setReality(String reality){this.reality = reality;}

    private void setReference(String reference){this.reference = reference;}

    private void setUrl(String url){this.url = url;}

    @Override
    public String toString(){
        return "Meme " + this.getId() + ": " +
        super.getName() + ".\n" + 
        "La realidad es: " + this.getReality() + ".\n" 
        + "Y la referencia que desmiente al bulo: " + this.getReference(); 
    }
}