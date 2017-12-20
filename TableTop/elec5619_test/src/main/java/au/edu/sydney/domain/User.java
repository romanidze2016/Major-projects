package au.edu.sydney.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;

    @Column(name = "first")
    private String first;

    @Column(name = "last")
    private String last;
    
    @Column(name = "email")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String newPass) {
    	this.password = newPass;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public boolean checkPass(String check) {
    	return password.equals(check);
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
    
    public boolean isValid() {
    	if (username.equals("") || first.equals("") || last.equals("") || email.equals("")) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    public boolean isValidPassword() {
    	if (password.length() < 6) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
}
