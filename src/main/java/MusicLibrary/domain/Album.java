package MusicLibrary.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Album extends AbstractPersistable<Long> {
    
    private String title;
    private int releasedIn;
    private String label;
    private Long artistId;
    
    /* 
    @ManyToMany (mappedBy = "albums")
    private List<StyleTag> tags;
    
    public List<StyleTag> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }
    */

    public Long getArtist() {
        return artistId;
    }

    public void setArtist(Long artistId) {
        this.artistId = artistId;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleasedIn() {
        return releasedIn;
    }

    public void setReleasedIn(int releasedIn) {
        this.releasedIn = releasedIn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
