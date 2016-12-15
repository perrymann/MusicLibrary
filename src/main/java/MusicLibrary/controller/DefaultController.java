/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicLibrary.controller;


import MusicLibrary.domain.Account;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {
        
        Account account1 = new Account();
        account1.setUsername("john");
        account1.setPassword(passwordEncoder.encode("rambokolmonen"));
        account1.setIsAdmin(true);
        accountRepo.save(account1);
        
        Account account2 = new Account();
        account2.setUsername("james");
        account2.setPassword(passwordEncoder.encode("bondbond"));
        account2.setIsAdmin(false);
        accountRepo.save(account2);
        
        Artist artist = new Artist();
        artist.setName("Keke");
        artistRepo.save(artist);
        StyleTag style1 = new StyleTag();
        style1.setName("Heavy");
        styleTagRepo.save(style1);
        StyleTag style2 = new StyleTag();
        style2.setName("Reggae");
        styleTagRepo.save(style2);
        
        // Album "I"
        Album album1 = new Album();
        album1.getTags().add(style1);
        album1.getTags().add(style2);
        album1.setArtist(artist);
        album1.setTitle("I");
        album1.setReleasedIn(1999);
        album1.setLabel("Poko");
        albumRepo.save(album1);
        
        // Album "II"
        Album album2 = new Album();
        album2.getTags().add(style1);
        //album2.getTags().add(style2);
        album2.setArtist(artist);
        album2.setTitle("II");
        album2.setReleasedIn(2000);
        album2.setLabel("Poko");
        albumRepo.save(album2);
        
        artist.getAlbums().add(album1);
        artist.getAlbums().add(album2);
        style1.getAlbums().add(album1);
        style2.getAlbums().add(album1);
        style1.getAlbums().add(album2);
        //style2.getAlbums().add(album2);
        styleTagRepo.save(style1);
        styleTagRepo.save(style2);
        artistRepo.save(artist);
    }
    
    @RequestMapping("*")
    public String handleDefault() {
        return "redirect:/artists";
    }
}
