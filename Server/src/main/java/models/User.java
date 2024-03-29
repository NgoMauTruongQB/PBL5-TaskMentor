package models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private int role;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "delete_time")
    private Timestamp deleteTime;

    @Column(name = "delete_by")
    private Integer deleteBy;

    // Constructors

    public User() {
    }

    public User(String email, String username, String password, int role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(Integer deleteBy) {
        this.deleteBy = deleteBy;
    }

    // Builder class
    public static class Builder {
        private String email;
        private String username;
        private String password;
        private int role;
        private String name;
        private String phone;
        private Timestamp deleteTime;
        private Integer deleteBy;

        public Builder(String email, String username, String password, int role) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder deleteTime(Timestamp deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public Builder deleteBy(Integer deleteBy) {
            this.deleteBy = deleteBy;
            return this;
        }

        public User build() {
            User user = new User();
            user.email = this.email;
            user.username = this.username;
            user.password = this.password;
            user.role = this.role;
            user.name = this.name;
            user.phone = this.phone;
            user.deleteTime = this.deleteTime;
            user.deleteBy = this.deleteBy;
            return user;
        }
    }
}
