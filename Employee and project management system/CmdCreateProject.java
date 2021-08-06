public class CmdCreateProject extends CmdRecorded{
    private Project lastProject;

    @Override
    public void execute(String[] inputs){
        try{
            Company c = Company.getInstance();
            lastProject = c.addProject(inputs[1]);
            addtoUndoStack(this);
            clearRedoStack();
        }
        catch (ExProjectAlreadyExists | ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        Project.reduceProjectCode();
        c.removeProject(lastProject.getName());
        addtoRedoStack(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        try{
            c.addProjectWithList(lastProject.getName());
            addtoUndoStack(this);
        }
        catch (ExProjectAlreadyExists | ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }
}
