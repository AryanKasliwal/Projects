public class ExWrongCommand extends Exception{
    public ExWrongCommand(){
        super("Wrong Command.");
    }

    public ExWrongCommand(String msg){
        super(msg);
    }
}
