/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicLibrary.systemTests;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_ArtistAlbumActionsTest extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;
    
    @Autowired
    private AccountRepository accountRepo;
    
    // Login for the tests
    @Before
    public void setUp() {
        // login
        accountRepo.deleteAll();
        Account account1 = new Account();
        account1.setUsername("martti");
        account1.setPassword("smith123");
        account1.setIsAdmin(true);
        accountRepo.save(account1);
        goTo("http://localhost:" + port);
        fill(find("#username")).with("martti");
        fill(find("#password")).with("smith123");
        submit(find("form").first());
        await().atMost(5, TimeUnit.SECONDS);
    }
    
    // Logged user can add artists
    @Test
    public void test1_artistCanBeAdded() {
        System.out.println("artistCanBeAdded()");
        goTo("http://localhost:" + port + "/artists/");
        fill(find("#artistName")).with("Van Halen");
        submit(find("form").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Van Halen"));
        fill(find("#artistName")).with("David Lee Roth");
        submit(find("form").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Van Halen"));
        assertTrue(pageSource().contains("David Lee Roth"));
        
    }
    
    // Logged user with admin status can delete artists
    @Test
    public void test2_artistCanBeDeleted() {
        System.out.println("artistCanBeDeleted()");
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Van Halen"));
        assertTrue(pageSource().contains("David Lee Roth"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Van Halen"));
        submit(find("form").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(!pageSource().contains("Van Halen"));
        assertTrue(pageSource().contains("David Lee Roth"));
    }
    
    // Logged user can access artists' pages
    @Test
    public void test3_artistPageCanBeAccessed() {
        System.out.println("test3_artistPageCanBeAccessed()");
        assertTrue(pageSource().contains("David Lee Roth"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(pageSource().contains("Add an Album"));
    }
    
    // ----------------------------
    // David Lee Roth is already saved as an artist
    
    @Test
    public void test4_albumCanBeAdded() {
        System.out.println("test4_albumCanBeAdded()");
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(!pageSource().contains("Skyscraper"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(pageSource().contains("Add an Album"));
        
        fill(find("#title")).with("Skyscraper");
        fill(find("#year")).with("1988");
        fill(find("#label")).with("Warner Bros.");
        submit(find("#add"));
        await().atMost(2, TimeUnit.SECONDS);
        
        assertTrue(pageSource().contains("Skyscraper"));
        assertTrue(!pageSource().contains("1988"));
        assertTrue(pageSource().contains("Add an Album"));
    }
    
    @Test
    public void test5_albumIndexCanBeAccessed() {
        System.out.println("test5_albumIndexCanBeAccessed()");
        goTo("http://localhost:" + port + "/albums/");
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Albums"));
        assertTrue(pageSource().contains("Skyscraper"));
    }
    
    
    
    
    // Album Skyscraper is already saved, let us access it
    
    @Test
    public void test6_albumPageCanBeAccessed() {
        System.out.println("test6_albumPageCanBeAccessed()");
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(!pageSource().contains("Skyscraper"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(pageSource().contains("Add an Album"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Skyscraper"));
        assertTrue(pageSource().contains("Styles associated"));
    }
    
    // Test deletion of "Skyscraper"
    
    @Test
    public void test7_albumCanBeDeleted() {
        System.out.println("test7_albumCanBeDeleted()");
        // navigation
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(!pageSource().contains("Skyscraper"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(pageSource().contains("Add an Album"));
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Skyscraper"));
        assertTrue(pageSource().contains("Styles associated"));
        // delete
        submit(find("form").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(!pageSource().contains("Skyscraper"));
        assertTrue(pageSource().contains("David Lee Roth"));
        assertTrue(pageSource().contains("Add an Album"));
    }
    
    
    // ----------------------------
    
    // Admin can access the admin page
    @Test
    public void test999_adminPageCanBeAccessed() {
        goTo("http://localhost:" + port + "/accounts/");
        assertTrue(pageSource().contains("Admins"));
        assertTrue(pageSource().contains("Users"));
    }
    
}
