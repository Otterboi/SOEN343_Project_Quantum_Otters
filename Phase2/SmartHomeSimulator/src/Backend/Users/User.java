package Backend.Users;
enum Role {STRANGER, GUEST, CHILD, PARENT}
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
        }else{
            role = Role.STRANGER;
        }

        name = n;
        password = p;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
