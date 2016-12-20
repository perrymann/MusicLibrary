package MusicLibrary.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;
 
@Entity
public class Account extends AbstractPersistable<Long> {
 
    @Column(unique = true)
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    
    @NotBlank
    @Length(min = 8,
            message = "Password must have at least 8 charaters")
    private String password;
    
    private String salt;    
    private boolean admin;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public boolean isAdmin() {
        return admin;
    }

    public void setIsAdmin(boolean admin) {
        this.admin = admin;
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
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }
 
}