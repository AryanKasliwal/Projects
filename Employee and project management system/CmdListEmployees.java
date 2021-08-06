public class CmdListEmployees implements command{
    @Override
    public void execute(String[] cmdInfo) {
        Company company = Company.getInstance();
        company.listAllEmployees();
    }
}
