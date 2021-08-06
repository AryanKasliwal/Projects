public class CmdGiveAssignmentSuggestions implements command{

    @Override
    public void execute(String[] inputs){
        try{
            Company c = Company.getInstance();
            c.giveAssignmentSuggestions(inputs[1]);
        }
        catch (ExProjectNotFound | ExNoSuggestions e){
            System.out.println(e.getMessage());
        }
    }
}
