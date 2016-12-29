
package MusicLibrary.integrationTests;

import MusicLibrary.controller.AlbumController;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumIntegrationTest {
    
    @Autowired
    private AlbumController albumController;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    @Test
    public void saveAlbumWorksForArtist(){
        Artist artist = new Artist();
        artist.setName("Martti Servo 1");
        artistRepo.save(artist);
        albumController.addAlbum(artist.getId(), "Parhaat1" , "1999", "Poko");
        assertNotNull(artistRepo.findOne(artist.getId()).getAlbums());
        
    }
    
    @Test
    public void saveAlbumWorksGenerally(){
        Artist artist = new Artist();
        artist.setName("Martti Servo");
        artistRepo.save(artist);
        albumController.addAlbum(artist.getId(), "Savea" , "1999", "Poko");
        assertEquals("Savea", albumRepo.findByTitle("Savea").getTitle());
    }
    
    @Test
    public void removeAlbumworksForAlbums(){
        albumRepo.deleteAll();
        Artist artist = new Artist();
        artist.setName("Martti Servo 2");
        artistRepo.save(artist);
        albumController.addAlbum(artist.getId(), "Parhaat2" , "1999", "Poko");
        albumController.deleteAlbum(albumRepo.findAll().get(0).getId());
        assertTrue(albumRepo.findAll().isEmpty());
    }
    
    @Test
    public void removeAlbumworksForArtist() {
        albumRepo.deleteAll();
        Artist artist = new Artist();
        artist.setName("Martti Servo 3");
        artistRepo.save(artist);
        albumController.addAlbum(artist.getId(), "Parhaat3" , "1999", "Poko");
        albumController.deleteAlbum(albumRepo.findAll().get(0).getId());
        assertTrue(artistRepo.findByName("Martti Servo 3").getAlbums().isEmpty());
    }
    
    // tää pitää testata
    @Test
    public void cantSaveAlbumWithoutParams(){
      
    }
    
      
}
