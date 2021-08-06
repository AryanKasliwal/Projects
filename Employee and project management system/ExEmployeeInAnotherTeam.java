public class ExEmployeeInAnotherTeam extends Exception{
    public ExEmployeeInAnotherTeam(){
        super("Employee already exists in another team.");
    }

    public ExEmployeeInAnotherTeam(Employee e){
        super("Employee " + e.getName() + " already belongs to team " + e.getTeam().getTeamName() + "!");
    }
}
