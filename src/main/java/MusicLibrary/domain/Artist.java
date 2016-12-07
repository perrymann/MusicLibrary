package MusicLibrary.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Artist extends AbstractPersistable<Long> {
    
    private String name;

    @OneToMany
    private List<Album> albums;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Album> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<>();
        }   
        return albums;
    }
}
