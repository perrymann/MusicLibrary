package MusicLibrary.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Artist extends AbstractPersistable<Long> {
    
    @NotBlank
    private String name;

    @OneToMany (mappedBy="artist", fetch = FetchType.EAGER)
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
