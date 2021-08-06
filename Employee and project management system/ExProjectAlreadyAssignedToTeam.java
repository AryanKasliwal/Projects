public class ExProjectAlreadyAssignedToTeam extends Exception{
    public ExProjectAlreadyAssignedToTeam(){
        super("Project has been assigned to team already!");
    }

    public ExProjectAlreadyAssignedToTeam(String msg){
        super(msg);
    }
}
