import java.util.Stack;

public abstract class CmdRecorded implements command{
     private static final Stack<CmdRecorded> undoStack = new Stack<>();
     private static final Stack<CmdRecorded> redoStack = new Stack<>();

     protected static void addtoUndoStack(CmdRecorded cmd){
         undoStack.push(cmd); 
     }
     protected static void addtoRedoStack(CmdRecorded cmd){
         redoStack.push(cmd);
     }
     protected static void clearRedoStack(){
         redoStack.clear();
     }

    public abstract void undoMe();
    public abstract void redoMe() throws ExEmployeeAlreadyExists, ExTeamNotFoundException, ExEmployeeNotFound, ExProjectNotFound, ExProjectAlreadyAssignedToTeam, ExProjectNotAssigned;

     public static void undoCommand () {
         if (undoStack.isEmpty())
             System.out.println("Nothing to undo.");
         else
             undoStack.pop().undoMe();
     }
     public static void redoCommand() {
        if (redoStack.isEmpty())
            System.out.println("Nothing to redo.");
        else{
            try{
                redoStack.pop().redoMe();
            }
            catch (ExEmployeeAlreadyExists | ExTeamNotFoundException | ExEmployeeNotFound | ExProjectNotFound | ExProjectAlreadyAssignedToTeam | ExProjectNotAssigned e){
                System.out.println(e.getMessage());
            }
        }
    }
}
