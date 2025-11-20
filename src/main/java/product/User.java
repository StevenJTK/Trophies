package product;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean isEnabled = false;

    protected User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username [id=" + id + ", username=" + username;
    }

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        if (roles == null) {
            throw new NullPointerException("Role doesn't exist");
        } else {
            roles.add(role);
        }
    }

    public void removeRole(Role role) {
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        if (roles.contains(role)) {
            roles.remove(role);
        } else {
            throw new IllegalArgumentException("Role does not exist.");
        }
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword()  {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}