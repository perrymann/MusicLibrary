package MusicLibrary.service;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private AccountRepository accountRepo;
    
    public Account currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepo.findByUsername(authentication.getName());
    }
}
