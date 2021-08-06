import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>{
    private final String name;
    private Team team;
    private ArrayList <Project> projects = new ArrayList<>();

    public Employee(String name){
        this.name = name;
    }

    public void addProject(Project p){
        projects.add(p);
        Collections.sort(projects);
    }

    public void removeProject(Project p){
        projects.remove(p);
    }

    public String employeesThatWorkedOnTheProject(Project parentProject){
        String ans = "";
        for (Project p : projects){
            ans += p.projectRelatedTo(parentProject);
        }
        if (ans.equals("")){
            return ans;
        }
        return ans.subSequence(0, ans.length() - 2).toString();
    }

    public String printProjects(){
        if (projects.size() == 0){
            return "(no project)";
        }
        String ans = "";
        for (int i = 0; i < projects.size(); i++){
            if (i != projects.size() - 1){
                ans += projects.get(i).getCode() + "(" + projects.get(i).getStatus().toString() + "), ";
            }
            else {
                ans +=  projects.get(i).getCode() + "(" + projects.get(i).getStatus().toString() + ")";
            }
        }
        return ans;
    }

    public void listEmployeeProjects(){
        System.out.println(this.name + ": " + this.printProjects());
    }

    public static void printExployeeParticipation(ArrayList <Employee> list){
        for (Employee e : list){
            e.listEmployeeProjects();
        }
    }

    public void addTeam(Team t){
        this.team = t;
    }

    public Team getTeam(){
        return this.team;
    }

    public String getName() {
        return name;
    }

    public static void listEmployees(ArrayList <Employee> list){
        for (Employee e: list){
            if (e.getTeam() == null){
                System.out.println(e.getName());
                continue;
            }
            System.out.println(e.getName() + " (" + e.getTeam().getTeamName() + ")");
        }
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameTosearch){
        nameTosearch = nameTosearch.trim();
        for (Employee e: list)
            if (e.getName().equals(nameTosearch))
                return e;
        return null;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
