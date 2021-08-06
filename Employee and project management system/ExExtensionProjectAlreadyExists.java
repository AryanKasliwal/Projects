public class ExExtensionProjectAlreadyExists extends Exception{
    public ExExtensionProjectAlreadyExists(){
        super("Extension already exists!");
    }

    public ExExtensionProjectAlreadyExists(String msg){
        super("Extension " + msg + " already exists!");
    }
}
