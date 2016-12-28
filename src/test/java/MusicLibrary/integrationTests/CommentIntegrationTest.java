
package MusicLibrary.integrationTests;

import MusicLibrary.controller.AlbumController;
import MusicLibrary.controller.CommentController;
import MusicLibrary.domain.Account;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.Comment;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.CommentRepository;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentIntegrationTest {
    
    private Album album;
    private Artist artist;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private AlbumController albumController;
    
    @Autowired
    private CommentController commentController;
    
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
        Account account1 = new Account();
        account1.setUsername("bubba");
        account1.setPassword("smith123");
        account1.setIsAdmin(true);
        accountRepo.save(account1);
    }
    
    @Test
    public void canSaveComment(){
        albumController.commentAlbum("Skeidaa!", albumRepo.findAll().get(0).getId().toString());
        Comment retrieved = commentRepo.findAll().get(0);
        assertEquals("Skeidaa!", retrieved.getContent());
    }
}
