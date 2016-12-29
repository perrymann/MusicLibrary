/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicLibrary.auth;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        
        String username = a.getPrincipal().toString();
        String password = a.getCredentials().toString();

        Account account = accountRepo.findByUsername(username);

        if (account == null) {
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        if (!BCrypt.hashpw(password, account.getSalt()).equals(account.getPassword())) {
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        String status = "USER";
        if (account.isAdmin()) {
            status = "ADMIN";
        }
        grantedAuths.add(new SimpleGrantedAuthority(status));

        return new UsernamePasswordAuthenticationToken(account.getUsername(), password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
}