public class Memes{
    private String name;

    public Memes(String name){
        this.setName(name);
    };

    private void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }

    public String toString(){
        return "Meme: " + this.getName();
    }

}