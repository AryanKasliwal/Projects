public class CmdHire extends CmdRecorded {
    private Employee hiredEmployee;

    @Override
    public void execute(String[] inputs) {
        Company company = Company.getInstance();
        try{
            hiredEmployee = company.addEmployee(inputs[1]);
            System.out.println("Done.");
            addtoUndoStack(this);
            clearRedoStack();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        company.fireEmployee(this.hiredEmployee.getName());
        addtoRedoStack(this);
    }

    @Override
    public void redoMe() throws ExEmployeeAlreadyExists {
        Company company = Company.getInstance();
        company.addEmployee(this.hiredEmployee.getName());
        addtoUndoStack(this);
    }
}
