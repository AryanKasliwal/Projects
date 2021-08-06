public class CmdMarkCompletion extends CmdRecorded{
    private Project lastProject;

    @Override
    public void execute(String[] inputs){
        try{
            Company c = Company.getInstance();
            c.setProjectCompletion(inputs[1]);
            lastProject = c.findProjectByCode(inputs[1]);
            System.out.println("Done.");
            addtoUndoStack(this);
            clearRedoStack();
        }
        catch (ExProjectNotFound | ExProjectNotAssigned | ExProjectAlreadyCompleted e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance(); 
        try{
            c.removeProjectCompletion(lastProject.getName());
            addtoRedoStack(this);
        }
        catch (ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe(){
        Company c = Company.getInstance();
        try{
            lastProject = c.setProjectCompletion(lastProject.getCode());
            addtoUndoStack(this);
        }
        catch (ExProjectNotFound | ExProjectNotAssigned | ExProjectAlreadyCompleted e){
            System.out.println(e.getMessage());
        }
    }
    
}
