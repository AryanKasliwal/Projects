import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ExWrongCommand{
        String filePath;
        Scanner in = new Scanner(System.in);
        Company c = Company.getInstance();
        System.out.print("Please input the file pathname: ");
        filePath = in.nextLine();
        Scanner file = file = new Scanner(new File(filePath));
        String input;
        while (file.hasNext()){
            input = file.nextLine();
            String [] inputs = input.split("\\|");
            if (input.equals("")){
                continue;
            }
            System.out.println("> " + input);
            if (inputs[0].equals("listEmployees")){
                (new CmdListEmployees()).execute(inputs);
            }
            else if (inputs[0].equals("listTeams")){
                (new CmdListTeams()).execute(inputs);
            }
            else if (inputs[0].equals("listProjects")){
                (new CmdListProjects()).execute(inputs);
            }
            else if (inputs[0].equals("undo")){
                CmdRecorded.undoCommand();
            }
            else if (inputs[0].equals("redo")){
                CmdRecorded.redoCommand();
            }
            else if (inputs[0].equals("listTeamProjects")){
                (new CmdListTeamProjects()).execute(inputs);
            }
            else if (inputs[0].equals("listStaffParticipations")){
                (new CmdListStaffParticipations()).execute(inputs);
            }
            else if (inputs[0].equals("giveAssignmentSuggestions")){
                (new CmdGiveAssignmentSuggestions()).execute(inputs);
            }
            else if (inputs.length == 1){
                try{
                    c.insuffientCommands(inputs);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else if (inputs[0].equals("hire")){
                (new CmdHire()).execute(inputs);
            }
            else if (inputs[0].equals("startNewDay")){
                (new CmdstartNewDay()).execute(inputs);
            }
            else if (inputs[0].equals("setupTeam")){
                (new CmdSetupTeam()).execute(inputs);
            }
            else if (inputs[0].equals("joinTeam")){
                (new CmdJoinTeam()).execute(inputs);
            }
            else if (inputs[0].equals("createProject")){
                (new CmdCreateProject()).execute(inputs);
            }
            else if (inputs[0].equals("assignProject")){
                (new CmdAssignProject()).execute(inputs);
            }
            else if (inputs[0].equals("markCompletion")){
                (new CmdMarkCompletion()).execute(inputs);
            }
            else if (inputs[0].equals("createExtensionProject")){
                (new CmdCreateExtensionProject()).execute(inputs);
            }
            else if (inputs[0].equals("listProjectStaff")){
                (new CmdListProjectStaff()).execute(inputs);
            }
            else{
                (new CmdUnknownCommand()).execute(inputs);
            }
        }
        file.close();
        in.close();
    }
}
