
package MusicLibrary.integrationTests;

import MusicLibrary.auth.JpaAuthenticationProvider;
import MusicLibrary.controller.AlbumController;
import MusicLibrary.controller.StyleTagController;
import MusicLibrary.domain.Account;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.CommentRepository;
import MusicLibrary.repository.StyleTagRepository;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagIntegrationTest {
    
    private Album album;
    private Artist artist;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    @Autowired
    private StyleTagController styleTagController;
    
    @Before
    public void setUp(){
        artist = new Artist();
        artist.setName("Matti");
        artistRepo.save(artist);
        album = new Album();
        album.setArtist(artist);
        album.setTitle("Savea");
        album.setReleasedIn(1970);
        album.setLabel("Indie");
        albumRepo.save(album);
    }
    
    @Test
    public void canAddTag(){
        styleTagController.create("Heavy", album.getId());
        assertNotNull(styleTagRepo.findByName("Heavy"));
    }
    
    @Test
    public void cannotAddDuplicateTags() {
        styleTagController.create("Heavy", album.getId());
        styleTagController.create("Heavy", album.getId());
        assertEquals(1, styleTagRepo.findAll().size());
    }
    
    // remove tag
}
