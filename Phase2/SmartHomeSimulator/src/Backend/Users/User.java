package Backend.Users;

public class User {

    private Role role;
    private String name;
    private String password;

    public User(){
        role = Role.STRANGER;
    }

    public User(String n, String p, String r){
        if(r.toLowerCase().equals("stranger")){
            role = Role.STRANGER;
        }else if(r.toLowerCase().equals("guest")){
            role = Role.GUEST;
        }else if(r.toLowerCase().equals("child")){
            role = Role.CHILD;
        }else if(r.toLowerCase().equals("parent")){
            role = Role.PARENT;
        }else if(r.toLowerCase().equals("admin")){
            role = Role.ADMIN;
        }
        else{
            role = Role.STRANGER;
        }

        name = n;
        password = p;

    }

    public Role getRole() {
        return role;
    }

    public String getRoleString(){return role.toString().toLowerCase();}

    public void setRole(Role role) {
        this.role = role;
    }
    public void setRole(String r){
        if(r.toLowerCase().equals("stranger")){
            role = Role.STRANGER;
        }else if(r.toLowerCase().equals("guest")){
            role = Role.GUEST;
        }else if(r.toLowerCase().equals("child")){
            role = Role.CHILD;
        }else if(r.toLowerCase().equals("parent")){
            role = Role.PARENT;
        }else if(r.toLowerCase().equals("admin")){
            role = Role.ADMIN;
        }
        else{
            role = Role.STRANGER;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
