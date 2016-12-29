
package MusicLibrary.systemTests;

import MusicLibrary.domain.Account;
import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
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
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.h2.command.dml.Select;
import org.openqa.selenium.By;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test2_AlbumActionsTest extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    // Login as an admin user
    @Before
    public void setUp() {
        // login
        accountRepo.deleteAll();
        Account account1 = new Account();
        account1.setUsername("mara");
        account1.setPassword("smith123");
        account1.setIsAdmin(true);
        accountRepo.save(account1);
        goTo("http://localhost:" + port);
        fill(find("#username")).with("mara");
        fill(find("#password")).with("smith123");
        submit(find("form").first());
        await().atMost(5, TimeUnit.SECONDS);
        // data setup;
        Artist a = new Artist();
        a.setName("Kiss");
        artistRepo.save(a);
    }
    
    @Test
    public void test1_canAddandDeleteComment() {
        System.out.println("test1_canAddandDeleteComment()");
        // Go to artist page
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Kiss"));
        click(find("a").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Add an Album"));
        // Add an album
        fill(find("#title")).with("Destroyer");
        fill(find("#year")).with("1976");
        fill(find("#label")).with("Casablanca");
        submit(find("#add"));
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Destroyer"));
        assertTrue(!pageSource().contains("1976"));
        assertTrue(pageSource().contains("Add an Album"));
        // Go to album page
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Styles"));
        // Add a comment
        fill(find("#content").first()).with("Best album ever!");
        submit(find("#comment").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("~"));
        assertTrue(pageSource().contains("Best album ever!"));
        // Delete the comment
        submit(find("#deleteComment"));
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(!pageSource().contains("~"));
        assertTrue(!pageSource().contains("Best album ever!"));
        assertTrue(pageSource().contains("Styles")); // Just to make sure that the view is the correct one
    }
    
    @Test
    public void test2_canAddTagsToDatabase() {
        System.out.println("test2_canAddTagsToDatabase()");
        // Go to artist page
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Kiss"));
        click(find("a").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Add an Album"));
        assertTrue(pageSource().contains("Destroyer"));
        // Go to album page
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Styles"));
        assertTrue(pageSource().contains("Destroyer"));
        // Add a new tag
        fill(find("#newTag")).with("Heavy");
        submit(find("#addNewTag"));
        await().atMost(2, TimeUnit.SECONDS);
        goTo("http://localhost:" + port + "/styletags");
        assertTrue(pageSource().contains("Style tags"));
        assertTrue(pageSource().contains("Heavy"));
    }
    
    @Test
    public void test3_cannotAddDuplicateTagsToDatabase() {
        System.out.println("test3_cannotAddDuplicateTagsToDatabase()");
        // Go to artist page
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Kiss"));
        click(find("a").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Add an Album"));
        assertTrue(pageSource().contains("Destroyer"));
        // Go to album page
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Styles"));
        assertTrue(pageSource().contains("Destroyer"));
        
        // Add a tag #1
        fill(find("#newTag")).with("Heavy");
        submit(find("#addNewTag"));
        await().atMost(2, TimeUnit.SECONDS);
        
        // Add a tag #2
        fill(find("#newTag")).with("Heavy");
        submit(find("#addNewTag"));
        await().atMost(2, TimeUnit.SECONDS);
        
        assertEquals(1, styleTagRepo.findAll().size());
    }
    
    @Test
    public void test4_canAttachTagToAnAlbum() {
        System.out.println("test4_canAttachTagToAnAlbum()");
        // Go to artist page
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Kiss"));
        click(find("a").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Add an Album"));
        assertTrue(pageSource().contains("Destroyer"));
        // Go to album page
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Styles"));
        assertTrue(pageSource().contains("Destroyer"));
        
        // Tag Album
        assertEquals(1, styleTagRepo.findAll().size());
        assertThat(find("#tagSelector").getTexts()).containsOnly("Heavy");
        click(find("#tagSelector").first());
        submit(find("#tagAlbum"));
        await().atMost(2, TimeUnit.SECONDS);
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        // Result is seen in the styletag index for example.
        assertTrue(pageSource().contains("Albums with a styletag"));
        assertTrue(pageSource().contains("Destroyer"));
    }
    
    @Test
    public void test5_canDeattachTagFromAlbum() {
        System.out.println("test5_canDeattachTagFromAlbum()");
        // Go to artist page
        goTo("http://localhost:" + port + "/artists/");
        assertTrue(pageSource().contains("Kiss"));
        click(find("a").first());
        await().atMost(5, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Add an Album"));
        assertTrue(pageSource().contains("Destroyer"));
        // Go to album page
        click(find("a").first());
        await().atMost(2, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Styles"));
        assertTrue(pageSource().contains("Destroyer"));
        // Tag selector is empty
        assertTrue(find("#tagSelector").isEmpty());
        // Deattach tag
        submit(find("#removeTag"));
        await().atMost(2, TimeUnit.SECONDS);
        // Tag is back in the tag selector
        assertThat(find("#tagSelector").getTexts()).containsOnly("Heavy");
    }
}
