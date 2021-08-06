public class CmdListProjects implements command{

    @Override
    public void execute(String[] cmdInfo) {
        Company c = Company.getInstance();
        c.listAllProjects();
    }
    
}
