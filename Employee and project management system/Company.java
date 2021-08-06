import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company {
    private final ArrayList<Employee> allEmployees;
    private final ArrayList<Team> allTeams;
    private final ArrayList<Project> allProjects;

    private static final Company instance = new Company();

    public static Company getInstance() {
        return instance;
    }

    private Company(){
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public void listTeams() {
        Team.list(allTeams);
    }

    public Team findTeam(String findName){
        return Team.searchTeam(allTeams, findName);
    }

    public Employee JoinTeam(String teamName, String employeeName) throws ExTeamNotFoundException, ExEmployeeNotFound, ExEmployeeInAnotherTeam{
        Employee e = findEmployee(employeeName);
        Team t = findTeam(teamName);
        if (t == null){
            throw new ExTeamNotFoundException(teamName);
        }
        if (e == null){
            throw new ExEmployeeNotFound(employeeName);
        }
        if (e.getTeam() != null){
            throw new ExEmployeeInAnotherTeam(e);
        }
        e.addTeam(t);
        t.addEmployees(e);
        return e;
    }

    public void removeEmployeeFromTeam(Employee e){
        Team t = e.getTeam();
        t.removeEmployee(e);
        e.addTeam(null);
    }

    public Team createTeam(String nT, String nE) throws ExEmployeeNotFound, ExEmployeeInAnotherTeam, ExTeamAlreadyExists{ // See how it is called in main()
        Employee e = Employee.searchEmployee(allEmployees, nE);
        if (e == null){
            throw new ExEmployeeNotFound(nE);
        }
        if (e.getTeam() != null){
            throw new ExEmployeeInAnotherTeam(e);
        }
        Team team = findTeam(nT);
        if (team != null){
            throw new ExTeamAlreadyExists();
        }
        Team t = new Team(nT,e);
        e.addTeam(t);
        allTeams.add(t);
        Collections.sort(allTeams); //allTeams
        return t; //Why return?  Ans: Later you'll find it useful for undoable comments.
    }

    public Team createTeam(Team t){
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    public void disbandTeam(Team t){
        allTeams.remove(t);
    }

    public void disbandTeam(String TeamName){
        allTeams.remove(Team.searchTeam(allTeams,TeamName));
    }

    public void listTeamProjects(){
        Team.listTeamProjects(allTeams);
    }

    public Employee addEmployee(String name) throws ExEmployeeAlreadyExists{ // See how it is called in main()
        if (findEmployee(name) != null){
            throw new ExEmployeeAlreadyExists();
        }
        Employee e = new Employee(name);
        allEmployees.add(e);
        Collections.sort(allEmployees); //allEmployees
        return e;
    }

    public void fireEmployee(Employee toFire){
        allEmployees.remove(toFire);
    }

    public void fireEmployee(String name){
        allEmployees.remove(Employee.searchEmployee(allEmployees,name));
    }

    public Employee findEmployee(String name){
        return Employee.searchEmployee(allEmployees, name);
    }

    public void listAllProjects(){
        Project.listAllProjects(allProjects);
    }

    public void listAllEmployees(){
        Employee.listEmployees(allEmployees);
    }

    public void listAllTeams(){
        Team.list(allTeams);
    }

    public void insuffientCommands(String [] inputs) throws ExInsufficientCommand{
        throw new ExInsufficientCommand();
    }

    public Project findProjectByName(String name) throws ExProjectNotFound{
        return Project.searchProjectByName(name, allProjects);
    }

    public Project findProjectByCode(String code) throws ExProjectNotFound{
        Project p =  Project.searchProjectByCode(code, allProjects);
        if (p == null){
            throw new ExProjectNotFound(code);
        }
        return p;
    }

    public Project addProject(String projectName) throws ExProjectAlreadyExists, ExProjectNotFound{
        if (findProjectByName(projectName) != null){
            throw new ExProjectAlreadyExists();
        }
        Project p = new Project(projectName);
        allProjects.add(p);
        Collections.sort(allProjects); //allEmployees
        return p;
    }

    public Project addProjectWithList(String name) throws ExProjectAlreadyExists, ExProjectNotFound{
        if (findProjectByName(name) != null){
            throw new ExProjectAlreadyExists();
        }
        Project p = new Project(name, allProjects);
        return p;
    }

    public void removeProject(String name){
        allProjects.removeIf(p -> (p.getName().equals(name)));
    }

    public void assignProjectToTeam(String pCode, String tName) throws ExProjectNotFound, ExTeamNotFoundException, ExProjectAlreadyAssignedToTeam{
        Project p = this.findProjectByCode(pCode);
        Team t = this.findTeam(tName);
        if (p == null){
            throw new ExProjectNotFound(pCode);
        }
        if (t == null){
            throw new ExTeamNotFoundException(tName);
        }
        if (p.getTeam() != null){
            throw new ExProjectAlreadyAssignedToTeam("Project " + p.getCode() + " has been assigned to team " + p.getTeamName() + " on " + p.getDayAssigned() + " already!");
        }
        p.setTeam(t);
        t.addProject(p);
    }

    public void unAssignProjectToEmployee(String pCode, String eName) throws ExProjectNotFound{
        Project p = findProjectByCode(pCode);
        Employee e = findEmployee(eName);
        p.removeSupportStaff(e);
        e.removeProject(p);
    }

    public void unAssignProjectToTeam(Project p, String tName){
        Team t = this.findTeam(tName);
        p.unSetTeam();
        t.removeProject(p);
    }

    public void removeSupportStaffFromProjectList(String [] inputs) throws ExProjectNotFound{
        Project p = findProjectByCode(inputs[1]);
        for (int i = 3; i < inputs.length; i++){
            Employee e = findEmployee(inputs[i]);
            p.removeSupportStaff(e);
        }
    }

    public Project setProjectCompletion(String code) throws ExProjectNotFound, ExProjectNotAssigned, ExProjectAlreadyCompleted{
        Project p = this.findProjectByCode(code);
        if (p == null){
            throw new ExProjectNotFound(code);
        }
        if (p.getStatus() instanceof PStatus_Pending){
            throw new ExProjectNotAssigned(code);
        }
        if (p.getStatus() instanceof PStatus_Completed){
            throw new ExProjectAlreadyCompleted(code);
        }
        p.markCompleted();
        return p;
    }

    public void removeProjectCompletion(String name) throws ExProjectNotFound{
        Project p = findProjectByName(name);
        p.unMarkCompleted();
    }

    public void throwWrongCommand() throws ExWrongCommand{
        throw new ExWrongCommand();
    }

    public void giveAssignmentSuggestions(String code) throws ExProjectNotFound, ExNoSuggestions{
        ExtensionProject childProject = (ExtensionProject)findProjectByCode(code);
        boolean firstTime1 = true, firstTime2 = true;
        Project parentProject = childProject.getParentProject();
        boolean suggestionExist = false;
        for (Team t : allTeams){
            String output = "";
            output += t.teamsThatWorkedOnTheProject(parentProject);
            if (output.equals("")){
                continue;
            }
            else{
                if (firstTime1){
                    System.out.println("These teams have worked on related projects:");
                    suggestionExist = true;
                    firstTime1 = false;
                }
                System.out.println(t.getTeamName() + ": " + output);
            }
        }
        for (Employee e : allEmployees){
            String output = "";
            output += e.employeesThatWorkedOnTheProject(parentProject);
            if (output.equals("")){
                continue;
            }
            else{
                if (firstTime2){
                    System.out.println("These staff have worked on related projects:");
                    firstTime2 = false;
                    suggestionExist = true;
                }
                System.out.println(e.getName() + ": " + output);
            }
        }
        if (!suggestionExist){
            throw new ExNoSuggestions();
        }
    }

    public ExtensionProject addNewExtensionProject(String pName, Project parent){
        ExtensionProject ep = new ExtensionProject(pName, parent);
        allProjects.add(ep);
        Collections.sort(allProjects);
        return ep;
    }

    public void undoAddExtensionProject(Project parent, Project child) throws ExProjectNotFound{
        Project c = findProjectByName(child.getName());
        allProjects.remove(c);
        parent.decreaseExtensionProjects();
    }

    public void checkSupportStaff(String projectCode, String name) throws ExProjectNotFound, ExEmployeeNotFound{
        Project p = findProjectByCode(projectCode);
        Employee e = findEmployee(name);
        if (e == null){
            throw new ExEmployeeNotFound(name);
        }
    }

    public void addSupportStaff(String projectCode, String eName) throws ExProjectNotFound{
        Project p = findProjectByCode(projectCode);
        Employee e = findEmployee(eName);
        e.addProject(p);
        p.addSupportStaff(e);
    }

    public void removeSupportStaff(String projectCode, String eName) throws ExProjectNotFound{
        Project p = findProjectByCode(projectCode);
        Employee e = findEmployee(eName);
        e.removeProject(p);
        p.removeSupportStaff(e);
    }

    public void listProjectStaff(String pCode) throws ExProjectNotFound{
        Project p = findProjectByCode(pCode);
        p.printStaff();
    }

    public void printEmployeeParticipation(){
        Employee.printExployeeParticipation(allEmployees);
    }
}