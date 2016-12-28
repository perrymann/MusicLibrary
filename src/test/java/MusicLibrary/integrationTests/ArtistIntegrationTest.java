
package MusicLibrary.integrationTests;

import MusicLibrary.controller.ArtistController;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistIntegrationTest {
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private ArtistController artistController;

    // Artist: Database operations
    
    // Saving artist works
    @Test
    public void saveArtistWorks(){
        artistController.addArtist("Martti Servo & Napander");
        Artist retrieved = artistRepo.findByName("Martti Servo & Napander");
        assertNotNull(retrieved);
        assertEquals("Martti Servo & Napander", retrieved.getName());
       
    }
    
    // Deleting a single artist without any albums works
    @Test
    public void deleteArtistWorks() {
        artistController.addArtist("Martti Servo & Napander");
        artistController.deleteArtist(artistRepo.findByName("Martti Servo & Napander").getId());
        Artist retrieved = artistRepo.findByName("Martti Servo & Napander");
        assertEquals(null, retrieved);
      
    }
    
    // Deleting a single artist with albums works
    @Test
    public void deleteArtistWithAlbums() {
        int initSize = albumRepo.findAll().size();
        artistController.addArtist("Martti");
        Album album = new Album();
        album.setArtist(artistRepo.findByName("Martti"));
        album.setTitle("Best");
        album.setLabel("Poko");
        album.setReleasedIn(2002);
        albumRepo.save(album);
        artistController.deleteArtist(artistRepo.findByName("Martti").getId());
        assertEquals(initSize, albumRepo.findAll().size());
        
    }
    
    // Two artists cannot have same name
    @Test
    public void cantSaveDuplicateArtists() {
        int sizeFirst = artistRepo.findAll().size();
        artistController.addArtist("Martti Servo & Napander");
        artistController.addArtist("Martti Servo & Napander");
        List<Artist> retrieved = artistRepo.findAll();
      
        assertEquals(sizeFirst + 1, retrieved.size());
        
       
    }
    
    // Artist cannot be saved without a name - sorry Prince aka Symbol
    @Test
    public void artistNameCannotBeEmpty() {
        int sizeFirst = artistRepo.findAll().size();
        artistController.addArtist("");
        assertEquals(sizeFirst, artistRepo.findAll().size());
    }
    
}