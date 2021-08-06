public class CmdUnknownCommand implements command{

    @Override
    public void execute(String[] cmdInfo){
        try{
            Company c = Company.getInstance();
            c.throwWrongCommand();
        }
        catch (ExWrongCommand e){
            System.out.println(e.getMessage());
        }
    }
    
}
