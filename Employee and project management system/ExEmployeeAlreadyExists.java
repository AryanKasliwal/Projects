public class ExEmployeeAlreadyExists extends Exception{ 
    public ExEmployeeAlreadyExists() {
        super("Employee name already exists!");
    }
    public ExEmployeeAlreadyExists(String msg){
        super("Employee name already exists!");
    }
}
