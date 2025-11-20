package product;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

    public void addUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        } else {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        } else {
            if(users.contains(user)) {
                users.remove(user);
            } else {
                throw new IllegalArgumentException("User is not assigned to this role.");
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }
}


