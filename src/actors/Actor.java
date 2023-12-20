package actors;

public abstract class Actor {
    public int id;
    protected String name;
    protected String surname;
    protected String className;
    protected String email;
    protected String description;

    public Actor(int id){
        loadActorInformation(id);
    }

    protected void loadActorInformation(int id){

    }
}
