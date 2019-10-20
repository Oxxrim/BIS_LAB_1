package ua.kpi.bis_lab_1.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private String login;
    private String password = "";
    private String role;
    private boolean block;
    private boolean limitations;

    public User() {
    }

    public User(String login, String password, Role role, boolean block, boolean limitations) {
        this.login = login;
        this.password = password;
        this.role = role.toString();
        this.block = block;
        this.limitations = limitations;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return Role.valueOf(role);
    }

    public void setRole(Role role) {
        this.role = role.toString();
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isLimitations() {
        return limitations;
    }

    public void setLimitations(boolean limitations) {
        this.limitations = limitations;
    }
}
