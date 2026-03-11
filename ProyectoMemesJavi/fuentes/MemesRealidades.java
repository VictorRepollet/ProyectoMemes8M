import java.util.*;

public class MemesRealidades extends Memes {
    private List<String> fakeRealities;

    private Long id;
    private String reality;
    private String reference;
    private String url;

    public MemesRealidades(Long id,String name, String reality, String reference, String url, List<String> fakeRealities){
        super(name);

        this.setId(id);
        this.setReality(reality);
        this.setReference(reference);
        this.setUrl(url);
        this.fakeRealities = fakeRealities;
    }

    public MemesRealidades(String name, String reality) {
        super(name);
        this.setReality(reality);
    }

    public Long getId(){return this.id;}

    public String getReality(){return this.reality;}

    public String getReference(){return this.reference;}

    public String getUrl(){return this.url;}

    public List<String> getFakeRealities() {return this.fakeRealities;}

    private void setId(Long id){this.id = id;}

    private void setReality(String reality){this.reality = reality;}

    private void setReference(String reference){this.reference = reference;}

    private void setUrl(String url){this.url = url;}

}