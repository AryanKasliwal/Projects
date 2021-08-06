public class ExtensionProject extends Project{
    public Project parentProject;
    
    public ExtensionProject(String pName, Project parent){
        super(parent, pName);
        parentProject = parent;
    }

    public Project getParentProject(){
        return this.parentProject;
    }
}
