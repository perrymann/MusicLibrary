package MusicLibrary.controller;

import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
