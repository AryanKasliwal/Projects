public class CmdJoinTeam extends CmdRecorded{
    private Employee e;
    private String lastCompanyName;
    @Override
    public void execute(String[] inputs){
        Company company = Company.getInstance();
        try{
            e = company.JoinTeam(inputs[1], inputs[2]);
            lastCompanyName = inputs[1];
            addtoUndoStack(this);
            clearRedoStack();
            System.out.println("Done.");
        }
        catch (ExTeamNotFoundException | ExEmployeeNotFound | ExEmployeeInAnotherTeam e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        company.removeEmployeeFromTeam(e);
        addtoRedoStack(this);
    }

    @Override
    public void redoMe(){
        Company c = Company.getInstance();
        try{
            e = c.JoinTeam(lastCompanyName, e.getName());
            addtoUndoStack(this);
        }
        catch (ExTeamNotFoundException | ExEmployeeNotFound | ExEmployeeInAnotherTeam e){
            System.out.println(e.getMessage());
        }
    }
}
