import java.util.ArrayList;
import java.util.Collections;

public class Project implements Comparable<Project>{
    private final String name;
    private final String code;
    private static int currentCode = 1;
    private final Day createdOn;
    private PStatus status;
    private Day dayAssigned;
    private Team assignedTo;
    private Day dayCompleted;
    private int numExtensionProjects = 0;
    private ArrayList <Employee> supportStaff = new ArrayList<>();
    private ArrayList <ExtensionProject> childProjects = new ArrayList<>();

    public Project(Project parent, String name){
        this.name = name;
        this.createdOn = SystemDate.getInstance().clone();
        this.status = new PStatus_Pending();
        parent.increaseExtensionProjects();
        this.code = calculateChildCode(parent.getCode(), parent.getNumExtensionProjects());
    }

    public Project findRelatedProjects(Project parentProject){
        for (ExtensionProject ep : childProjects){
            if (ep.getParentProject() == parentProject){
                return ep;
            }
        }
        return null;
    }

    public String projectRelatedTo(Project parentProject){
        String ans = "";
        if (this instanceof ExtensionProject){
            ExtensionProject ep = (ExtensionProject) this;
            if (ep.getParentProject() == parentProject){
                ans += ep.getCode() + "(" + ep.getStatus() + "), ";
            }
        }
        else{
            if (this == parentProject){
                ans += this.getCode() + "(" + this.getStatus() + "), ";
            }
        }
        return ans;
    }

    public void addChildProject(ExtensionProject p){
        this.childProjects.add(p);
    }
    
    public Project(String name){
        this.name = name;
        code = calculateProjectCode(currentCode);
        addProjectCode();
        createdOn = SystemDate.getInstance().clone();
        this.status = new PStatus_Pending();
        System.out.println("Project created: [" + code + "] " + this.getName() + " (" + this.getCreatedDay() + ")");
    }

    public Project(String name, ArrayList <Project> list){
        this.name = name;
        code = calculateProjectCode(currentCode);
        addProjectCode();
        createdOn = SystemDate.getInstance().clone();
        this.status = new PStatus_Pending();
        list.add(this);
        Collections.sort(list);
    }

    public void addSupportStaff(Employee e){
        supportStaff.add(e);
        Collections.sort(supportStaff);
    }

    public void removeSupportStaff(Employee e){
        supportStaff.remove(e);
    }

    public int getNumExtensionProjects(){
        return this.numExtensionProjects;
    }

    public void increaseExtensionProjects(){
        this.numExtensionProjects++;
    }

    public void decreaseExtensionProjects(){
        this.numExtensionProjects--;
    }

    public void markCompleted(){
        this.dayCompleted = SystemDate.getInstance().clone();
        this.projectCompleted();
    }

    public void unMarkCompleted(){
        this.dayCompleted = null;
        this.projectInProgress();
    }

    public void unMarkInProgress(){
        this.dayAssigned = null;
        this.projectPending();
    }

    public void setAssignedOn(Day d){
        this.dayAssigned = d;
    }

    public void setTeam(Team t){
        this.assignedTo = t;
        this.projectInProgress();
        this.setAssignedOn(SystemDate.getInstance().clone());
    }

    public void setTeamToNull(){
        this.assignedTo = null;
    }

    public void unSetTeam(){
        this.projectPending();
        this.setAssignedOn(null);
        this.setTeamToNull();
    }

    public static Project searchProjectByName(String name, ArrayList <Project> list){
        for (Project p : list){
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public static Project searchProjectByCode(String code, ArrayList <Project> list){
        for (Project p : list){
            if (p.getCode().equals(code)){
                return p;
            }
        }
        return null;
    }

    private String calculateChildCode(String parentCode, int parentExtensionNum){
        return parentCode + "-E" + Integer.toString(parentExtensionNum);
    }

    private String calculateProjectCode(int n){
        int length = Integer.toString(n).length();
        String ans = "P";
        for (int i = 0; i < 3-length; i++){
            ans += "0";
        }
        ans += Integer.toString(n);
        return ans;
    }

    public static void addProjectCode(){
        currentCode++;
    }

    public static void reduceProjectCode(){
        currentCode--;
    }

    public String getDayCompleted(){
        if (this.dayCompleted == null){
            return "--";
        }
        return this.dayCompleted.toString();
    }

    public void projectCompleted(){
        this.status = new PStatus_Completed();
    }

    public void projectInProgress(){
        this.status = new PStatus_InProgress();
    }

    public void projectPending(){
        this.status = new PStatus_Pending();
    }

    public String getDayAssigned(){
        if (this.dayAssigned == null){
            return "--";
        }
        return this.dayAssigned.toString();
    }

    public Team getTeam(){
        return this.assignedTo;
    }

    public PStatus getStatus(){
        return this.status;
    }

    public String getCode(){
        return this.code;
    }

    public Day getCreatedDay(){
        return this.createdOn;
    }

    public String getName(){
        return this.name;
    }

    public String getTeamName(){
        if (this.assignedTo == null){
            return "--";
        }
        return this.assignedTo.getTeamName();
    }

    @Override
    public int compareTo(Project another) {
        return this.getCode().compareTo(another.getCode());
    }

    public static void listAllProjects(ArrayList<Project> list){
        System.out.printf("%-9s%-23s%-13s%-13s%-14s%-13s%-13s\n", "Code", "Project Title", "Created on", "Status", "Assigned to", "Assigned on", "Completed on");
        for (Project p : list){
            System.out.printf("%-9s%-23s%-13s%-13s%-14s%-13s%-13s\n", p.getCode(), p.getName(), p.getCreatedDay(), p.getStatus(), p.getTeamName(), p.getDayAssigned(), p.getDayCompleted());
        }
    }

    public String printSupportStaff(){
        if (supportStaff.size() == 0){
            return "(none)";
        }
        String ans = "";
        for (int i = 0; i < supportStaff.size(); i++){
            if (i != supportStaff.size() - 1){
                ans += supportStaff.get(i).getName() + ", ";
            }
            else{
                ans += supportStaff.get(i).getName();
            }
        }
        return ans;
    }

    public void printStaff(){
        System.out.println("Project team: " + this.getTeamName());
        System.out.println("Project team members: " + this.getTeam().printMembers());
        System.out.println("External support: " + this.printSupportStaff());
    }
}
