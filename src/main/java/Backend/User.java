package Backend;

public abstract class User {
    private int id;
    private String name;
    private String password;

    public User(int id, String name, String password)
    {
        this.id=id;
        this.name=name;
        this.password=password;
    }

    // Changed from protected to public so the frontend classes can access these methods
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}

    //setters
    // Changed from protected to public
    public void setPassword(String passnew){this.password=passnew;}
    public void setId(int newID){this.id=newID;}
    public void setName(String newname){this.name=newname;}
    //protected boolean login(int id, String Password){}

}
