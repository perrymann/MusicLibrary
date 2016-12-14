
package MusicLibrary.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

// describes characteristics associated with an album. 
// A single album can have many of them, such as "Instrumental", "Atmospheric", "Heavy"
// uutta luoteassa validoitava, ettei samaa tägiä luoda uudestaan"

@Entity
public class StyleTag extends AbstractPersistable<Long> {

    private String name;
    @ManyToMany
    private List<Album> albums; 
    
    public List<Album> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<>();
        } 
        return albums;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
