package MusicLibrary.controller;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    
//    @Autowired
//    private AccountRepository accountRepo;
//    
//    @PostConstruct
//    public void init() {
//        Account account1 = new Account();
//        account1.setUsername("kari");
//        account1.setPassword("smith123");
//        account1.setIsAdmin(true);
//        accountRepo.save(account1);
//    }

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
