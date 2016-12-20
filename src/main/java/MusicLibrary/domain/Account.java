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
    @NotBlank(message = "Please choose username between 2 and 30 letters.")
    private String username;
    
    @NotBlank
    @Length(min = 8)
    private String password;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    private String salt;    
    private boolean admin;

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