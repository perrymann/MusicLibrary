/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicLibrary.integrationTests;

import MusicLibrary.controller.AlbumController;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
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
         
    private Artist artist;
    
    @Before
    public void setUp(){
        artist = new Artist();
        artist.setName("Martti Servo");
        artistRepo.save(artist);
    }
    
    @Test
    public void saveAlbumWorks(){
        albumController.addAlbum(artist.getId(), "Parhaat" , "1999", "Poko");
        assertNotNull(artistRepo.findOne(artist.getId()).getAlbums());
        
    }
    @Test
    public void removeAlbumworks(){
        albumController.addAlbum(artist.getId(), "Parhaat" , "1999", "Poko");
        albumController.deleteAlbum(albumRepo.findAll().get(0).getId());
        assertEquals(0, albumRepo.findAll().size());
    }
    
    // tää pitää testata
    @Test
    public void cantSaveAlbumWithoutParams(){
      
    }
    
      
}
