
package MusicLibrary.systemTests;

import MusicLibrary.domain.Account;
import MusicLibrary.repository.AccountRepository;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MusicLibraryAuthTest extends FluentTest {
    
    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Before
    public void setUp() {
        accountRepo.deleteAll();
        Account account1 = new Account();
        account1.setUsername("martti");
        account1.setPassword("smith123");
        account1.setIsAdmin(true);
        accountRepo.save(account1);
    }
   
    @Test
    public void canSignUp() {
        goTo("http://localhost:" + port);

        click(find("a").first());
        assertEquals("Signup", title());

        fill(find("#username")).with("Bob");
        fill(find("#password")).with("bobobobo");
        submit(find("form").first());

        assertTrue(pageSource().contains("Login"));
    }
    
    @Test
    public void canLogin() {
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains("Login"));
        fill(find("#username")).with("martti");
        fill(find("#password")).with("smith123");
        submit(find("form").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Artists"));
    }
    @Test
    public void canLogout() {
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains("Login"));
        fill(find("#username")).with("martti");
        fill(find("#password")).with("smith123");
        submit(find("form").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Artists"));
        submit(find("#logout"));
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Login"));
    }
}
