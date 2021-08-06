public class ExEmployeeNotFound extends Exception{
    public ExEmployeeNotFound() {
        super("Employee is not found!");
    }
    public ExEmployeeNotFound(String msg){
        super("Employee " + msg + " is not found!");
    }
}
