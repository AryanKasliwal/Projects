public class ExProjectNotAssigned extends Exception{
    public ExProjectNotAssigned(){
        super("Assignment of project has not been started!");
    }

    public ExProjectNotAssigned(String msg){
        super("Assignment of project " + msg + " has not been started!");
    }
}
