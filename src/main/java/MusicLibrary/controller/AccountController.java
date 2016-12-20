
package MusicLibrary.controller;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;

    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute Account account) {
        account.setIsAdmin(false);
        System.out.println(account.getUsername() + "," + account.getPassword() + ", is admin: " + account.isAdmin());
        accountRepo.save(account);
        return "redirect:/login";
    }
}
