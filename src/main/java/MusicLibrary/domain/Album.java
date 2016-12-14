package MusicLibrary.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Album extends AbstractPersistable<Long> {
    
    @NotBlank
    private String title;
    
    @NotNull
    @Min(1900)
    @Max(9999)
    private Integer releasedIn;
    
    @NotBlank
    private String label;
    
    @ManyToOne 
    private Artist artist;
    
    @OneToMany (mappedBy="album", fetch = FetchType.EAGER)
    private List<Comment> comments;
    
    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }
    
    public void setComment(Comment comment) {
        getComments().add(comment);
    }
     
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    @ManyToMany (mappedBy= "albums")
    private List<StyleTag> styleTags;
    
    public List<StyleTag> getTags() {
        if (styleTags == null) {
            styleTags = new ArrayList<>();
        }
        return styleTags;
    }
    
   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleasedIn() {
        return releasedIn;
    }

    public void setReleasedIn(Integer releasedIn) {
        this.releasedIn = releasedIn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
