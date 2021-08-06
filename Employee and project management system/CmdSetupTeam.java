public class CmdSetupTeam extends CmdRecorded{
    private Team team;

    @Override
    public void execute(String[] inputs) {
        try{
            Company company = Company.getInstance();
            team = company.createTeam(inputs[1],inputs[2]);
            addtoUndoStack(this);
            clearRedoStack();
            System.out.println("Done.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        company.disbandTeam(team);
        addtoRedoStack(this);
    }

    @Override
    public void redoMe() {
        Company company = Company.getInstance();
        company.createTeam(team);
        addtoUndoStack(this);
    }
}
