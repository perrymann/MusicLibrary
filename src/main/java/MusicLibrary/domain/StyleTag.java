
package MusicLibrary.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

// describes characteristics associated with an album. 
// A single album can have many of them, such as "Instrumental", "Atmospheric", "Heavy"
// uutta luoteassa validoitava, ettei samaa tägiä luoda uudestaan"

@Entity
public class StyleTag extends AbstractPersistable<Long> {

    private String description;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
