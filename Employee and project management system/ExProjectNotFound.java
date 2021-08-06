public class ExProjectNotFound extends Exception{
    public ExProjectNotFound(){
        super("Project not found!");
    }

    public ExProjectNotFound(String msg){
        super("Project " + msg + " is not found!");
    }
}
