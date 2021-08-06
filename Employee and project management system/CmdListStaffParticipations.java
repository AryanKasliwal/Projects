public class CmdListStaffParticipations extends CmdRecorded{

    @Override
    public void execute(String[] cmdInfo) {
        try{
            Company c = Company.getInstance();
            c.printEmployeeParticipation();
        }
        catch (Exception e){
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
