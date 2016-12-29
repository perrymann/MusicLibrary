package MusicLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    
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
