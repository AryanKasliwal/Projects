public class ExTeamNotFoundException extends Exception{ 
    public ExTeamNotFoundException() {
        super("Team not found.");
    }
    public ExTeamNotFoundException(String msg){
        super("Team " + msg + " is not found!");
    }
}
