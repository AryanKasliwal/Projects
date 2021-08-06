public class ExNoSuggestions extends Exception{
    public ExNoSuggestions(){
        super("No team or staff has worked on related projects.");
    }

    public ExNoSuggestions(String msg){
        super(msg);
    }
}
