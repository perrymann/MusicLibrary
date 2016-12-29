package MusicLibrary.controller;


import MusicLibrary.domain.Account;
//import MusicLibrary.domain.Album;
//import MusicLibrary.domain.Artist;
//import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
   
    @RequestMapping("*")
    public String handleDefault() {
        return "redirect:/artists";
    }
    
    @RequestMapping("/login")
    public String loginForm(){
        return "login";
    }
    
    @RequestMapping("/signup")
    public String signupForm(){
        return "signup";
    }
}
