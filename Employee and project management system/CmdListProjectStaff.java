public class CmdListProjectStaff extends CmdRecorded{

    @Override
    public void execute(String[] inputs) {
        try{
            Company c = Company.getInstance();
            Project p = c.findProjectByCode(inputs[1]);
            if (p.getTeam() == null){
                throw new ExProjectNotAssigned(inputs[1]);
            }
            p.printStaff();
        }
        catch (ExProjectNotAssigned | ExProjectNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redoMe() throws ExEmployeeAlreadyExists, ExTeamNotFoundException, ExEmployeeNotFound, ExProjectNotFound,
            ExProjectAlreadyAssignedToTeam, ExProjectNotAssigned {
        // TODO Auto-generated method stub
        
    }
    
}
