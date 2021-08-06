import java.util.*;
import java.util.Collections;

public class Team implements Comparable<Team>{
    private final String teamName;
    private final Employee head;
    private final Day dateSetup;
    private ArrayList <Project> projects = new ArrayList<>();
    private ArrayList <Employee> employees = new ArrayList<>();

    public Team (String name, Employee head){
        this.teamName = name;
        this.head = head;
        dateSetup = SystemDate.getInstance().clone();
    }

    public String getAllEmployees(){
        String ans = "";
        if (employees.size() == 0){
            ans += head.getName() +"(The Leader)";
        }
        else{
            ans += head.getName() +"(The Leader), ";
        }
        for (int i = 0; i < employees.size(); i++){
            if (i != employees.size() - 1){
                ans += employees.get(i).getName() + ", ";
            }
            else{
                ans += employees.get(i).getName();
            }
        }
        return ans;
    }

    public void removeProject(Project p){
        this.projects.remove(p);
        this.head.removeProject(p);
        for (Employee e : employees){
            e.removeProject(p);
        }
    }

    public String teamsThatWorkedOnTheProject(Project parentProject){
        String ans = "";
        for (Project p : this.projects){
            ans += p.projectRelatedTo(parentProject);
        }
        if (ans.equals("")){
            return ans;
        }
        return ans.subSequence(0, ans.length() - 2).toString();
    }

    public String getProjectInfo(){
        String ans = "";
        for (int i = 0; i < this.projects.size(); i++){
            ans += this.projects.get(i).getCode();
            ans += "(" + this.projects.get(i).getStatus() + ")";
            if (i != this.projects.size() - 1){
                ans += ", ";
            }
        }
        return ans;
    }

    public static void listTeamProjects(ArrayList <Team> list){
        for (Team t : list){
            System.out.println(t.getTeamName() + ": " + t.getProjectInfo());
        }
    }

    public void addProject(Project p){
        this.head.addProject(p);
        for (Employee e : employees){
            e.addProject(p);
        }
        this.projects.add(p);
        Collections.sort(projects);
    }

    public String getTeamName(){
        return this.teamName;
    }

    public void addEmployees(Employee e){
        employees.add(e);
        Collections.sort(employees);
    }

    public void removeEmployee(Employee e){
        employees.remove(e);
    }

    public String printTeamMembers(){
        String ans = "";
        for (int i = 0; i < employees.size(); i++){
            if (i == employees.size() - 1){
                ans += employees.get(i).getName();
            }
            else {
                ans += employees.get(i).getName() + ", ";
            }
        }
        return ans;
    }

    public String printMembers(){
        String ans = head.getName() + " (The Leader)";
        if (employees.size() != 0){
            ans += ", ";
        }
        for (int i = 0; i < employees.size(); i++){
            if (i == employees.size() - 1){
                ans += employees.get(i).getName();
            }
            else {
                ans += employees.get(i).getName() + ", ";
            }
        }
        return ans;
    }

    public static void list(ArrayList<Team> list){
        System.out.printf("%-15s%-10s%-13s%s\n", "Team Name", "Leader", "Setup Date", "Members");
        for (Team t: list){
            String names = t.printTeamMembers();
            if (names.length() != 0){
                System.out.printf("%-15s%-10s%-13s%s\n",t.teamName,t.head.getName(),t.dateSetup, names);
                continue;
            }
            System.out.printf("%-15s%-10s%-13s%s\n",t.teamName,t.head.getName(),t.dateSetup, "(no member)");
        }
    }

    public static Team searchTeam(ArrayList<Team> list, String name){
        for (Team t: list)
            if (t.teamName.equals(name))
                return t;
        return null;

    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }
}
