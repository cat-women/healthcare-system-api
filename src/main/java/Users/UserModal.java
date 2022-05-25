/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.sql.Date;

/**
 *
 * @author Dell
 */
public class UserModal {
    private int id,token;
    String name ,username,email,password,userType;
    Date date;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getToken() {
        return token;
    }

    public String getUserType() {
        return userType;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
