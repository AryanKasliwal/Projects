public class ExTeamAlreadyExists extends Exception{
    public ExTeamAlreadyExists(){
        super("Team name already exists!");
    }

    public ExTeamAlreadyExists(String msg){
        super(msg);
    }
}
