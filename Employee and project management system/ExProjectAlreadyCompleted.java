public class ExProjectAlreadyCompleted extends Exception{
    public ExProjectAlreadyCompleted(){
        super("Project has been marked as completed before!");
    }

    public ExProjectAlreadyCompleted(String msg){
        super("Project " + msg + " has been marked as completed before!");
    }
}
