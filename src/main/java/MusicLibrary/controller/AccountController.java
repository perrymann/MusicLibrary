
package MusicLibrary.controller;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private UserService us;

    @Secured("ADMIN")
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String getAccounts(Model model){
        List<Account> admins = new ArrayList<>();
        List<Account> users = new ArrayList<>();
        for (Account a : accountRepo.findAll()) {
            if (a.isAdmin() && !us.currentUser().equals(a)) {
                admins.add(a) ;
            } else if (!a.isAdmin()) {
                users.add(a);
            }
        }
        model.addAttribute("admins", admins);
        model.addAttribute("users", users);
        return "accounts";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return "signup"; }
        account.setIsAdmin(false);
        accountRepo.save(account);
        return "redirect:/login";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String view(@ModelAttribute Account account) {
        return "signup";
    }
    
    
}
