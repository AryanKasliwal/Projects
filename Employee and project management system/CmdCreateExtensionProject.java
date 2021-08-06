public class CmdCreateExtensionProject extends CmdRecorded{
    private Project lastExtensionParentProject;
    private ExtensionProject lastExtensionChildProcess;
    private String lastProjectName;

    @Override
    public void execute(String[] inputs) {
        try{
            Company c = Company.getInstance();
            Project p = c.findProjectByCode(inputs[1]);
            lastProjectName = inputs[2];
            lastExtensionChildProcess = c.addNewExtensionProject(inputs[2], p);
            p.addChildProject(lastExtensionChildProcess);
            System.out.println("Project created: [" + lastExtensionChildProcess.getCode()+ "] " + lastExtensionChildProcess.getName() + " (" + lastExtensionChildProcess.getCreatedDay() + ")");
            lastExtensionParentProject = p;
            addtoUndoStack(this);
            clearRedoStack();
        }
        catch (ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe(){
        try{
            Company c = Company.getInstance();
            c.undoAddExtensionProject(lastExtensionParentProject, lastExtensionChildProcess);
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
            c.addNewExtensionProject(lastProjectName, lastExtensionParentProject);
            addtoUndoStack(this);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
