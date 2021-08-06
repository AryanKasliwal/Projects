public class CmdListTeamProjects extends CmdRecorded{

    @Override
    public void execute(String[] inputs){
        Company c = Company.getInstance();
        c.listTeamProjects();
    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redoMe() throws ExEmployeeAlreadyExists, ExTeamNotFoundException, ExEmployeeNotFound {
        // TODO Auto-generated method stub
        
    }
    
}
