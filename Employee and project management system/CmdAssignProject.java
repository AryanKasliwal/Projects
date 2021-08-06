public class CmdAssignProject extends CmdRecorded{
    private String [] inputs;
    private Project lastProject;
    private Team lastTeam;

    @Override
    public void execute(String[] inputs){
        try{
            Company c = Company.getInstance();
            this.inputs = inputs;
            for (int i = 3; i < inputs.length; i++){
                c.checkSupportStaff(inputs[1], inputs[i]);
            }
            for (int i = 3; i < inputs.length; i++){
                c.addSupportStaff(inputs[1], inputs[i]);
            }
            c.assignProjectToTeam(inputs[1], inputs[2]);
            lastProject = c.findProjectByCode(inputs[1]);
            lastTeam = c.findTeam(inputs[2]);
            System.out.println("Done.");
            addtoUndoStack(this);
            clearRedoStack();
        }
        catch (ExEmployeeNotFound | ExTeamNotFoundException | ExProjectNotFound | ExProjectAlreadyAssignedToTeam e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe(){
        try{
            Company c = Company.getInstance();
            c.unAssignProjectToTeam(lastProject, lastTeam.getTeamName());
            for (int i = 3; i < inputs.length; i++){
                c.unAssignProjectToEmployee(inputs[1], inputs[i]);
            }
            addtoRedoStack(this);
        }
        catch (ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe(){
        Company c = Company.getInstance();
        try {
            c.assignProjectToTeam(lastProject.getCode(), lastTeam.getTeamName());
            for (int i = 3; i < inputs.length; i++){
                c.addSupportStaff(inputs[1], inputs[i]);
            }
            addtoUndoStack(this); 
        }
        catch ( ExTeamNotFoundException | ExProjectNotFound | ExProjectAlreadyAssignedToTeam e){
            System.out.println(e.getMessage());
        }       
    }
    
}
