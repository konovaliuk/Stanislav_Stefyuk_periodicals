package entities;

import jakarta.persistence.*;

@Table(name = "user")
@Entity
@NamedQuery(name = "User.findAll",
        query = "select a from User a")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "mail")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    public User(long Id, String Name, String Email, String Password, String Role){
        this.id = Id;
        this.name = Name;
        this.email = Email;
        this.password = Password;
        this.role = Role;
    }

    public User(String Name, String Email, String Password, String Role){
        this.name = Name;
        this.email = Email;
        this.password = Password;
        this.role = Role;
    }

    public User(String Name, String Email, String Password){
        this.name = Name;
        this.email = Email;
        this.password = Password;
        this.role = "reader";
    }

    public User(){}

    public long getId(){return this.id;}
    public void setId(long value){this.id = value;}

    public String getName(){return this.name;}
    public void setName(String value){this.name=value;}

    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String value)
    {
        this.email = value;
    }

    public String getPassword(){return this.password;}
    public void setPassword(String value){this.password=value;}

    public String getRole(){return this.role;}
    public void setRole(String value){this.role=value;}
}
