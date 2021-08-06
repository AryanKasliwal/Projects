public class CmdListTeams implements command {

    @Override
    public void execute(String[] cmdInfo) {
        Company company = Company.getInstance();
        company.listAllTeams();
    }
}
