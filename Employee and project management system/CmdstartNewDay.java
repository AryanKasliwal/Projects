public class CmdstartNewDay extends CmdRecorded{
    private String oldDay;
    private String newDay;

    @Override
    public void execute(String[] cmdInfo) {
        if (SystemDate.getInstance() == null){
            SystemDate.createTheInstance(cmdInfo[1]);
        }
        else{
            SystemDate date = SystemDate.getInstance();
            oldDay = date.toString();
            newDay = cmdInfo[1];
            date.set(newDay);
            addtoUndoStack(this);
            clearRedoStack();
            System.out.println("Done.");
        }
    }

    @Override
    public void undoMe() {
        SystemDate date = SystemDate.getInstance();
        date.set(oldDay);
        addtoRedoStack(this);
    }

    @Override
    public void redoMe() {
        SystemDate date = SystemDate.getInstance();
        date.set(newDay);
        addtoUndoStack(this);
    }


}
