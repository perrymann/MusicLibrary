
package MusicLibrary.controller;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.CommentRepository;
import MusicLibrary.repository.StyleTagRepository;
import MusicLibrary.service.AlbumCommentService;
import MusicLibrary.service.AlbumStyleTagService;
import MusicLibrary.service.ArtistAlbumService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlbumController {
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private ArtistAlbumService aas;
    
    @Autowired
    private AlbumStyleTagService ass;
    
    @Autowired
    private AlbumCommentService acs;
    
    // view puuttuu
    @RequestMapping("/albums")
    public String index(Model model) {
        model.addAttribute("albums", albumRepo.findAll());
        return "albumIndex";
    }
    
    // Fetches a single album; Path is called from a context of a certain artist
    
    @RequestMapping("/albums/{id}")
    public String getAlbum(Model model, @PathVariable Long id) {
        Album album = albumRepo.findOne(id);
        if (album != null) {
            model.addAttribute("album", album);
            model.addAttribute("albumStyles", album.getTags());
            
            // Checks all available styletags in order to avoid duplicates
            List<StyleTag> availableStyleTags = new ArrayList<>();
            for (StyleTag tag : styleTagRepo.findAll()) {
                if (!album.getTags().contains(tag)) {
                    availableStyleTags.add(tag);
                }
            }
            model.addAttribute("styleTags", availableStyleTags);
            model.addAttribute("comments", commentRepo.findByAlbum(album));
            return "albumPage";
        } else {
            return "redirect:/*";
        }    
    }
    
    // Add an album for an artist
    
    @RequestMapping(value="/artists/{id}", method=RequestMethod.POST)
    public String addAlbum(@PathVariable Long id, @RequestParam String title, @RequestParam String year, @RequestParam String label){
        Artist artist = artistRepo.findOne(id);
        for (Album album : albumRepo.findByArtist(artist)) {
            if (album.getTitle().equals(title)) {
                return "redirect:/artists/" + id;
            }
        }
        Album x = new Album();
        x.setTitle(title);
        x.setReleasedIn(Integer.parseInt(year));
        x.setLabel(label);
        x.setArtist(artist);
        albumRepo.save(x);
        aas.addAlbumToArtist(x, id);
        return "redirect:/artists/" + id;
    }
    
    // Delete an album. the request is also forwarded to the ArtistAlbumService that handles the interaction between an album and an artist 
    
    @Secured("ADMIN")
    @RequestMapping(value="/albums/{id}", method=RequestMethod.DELETE)
    public String deleteAlbum(@PathVariable Long id) {
        Album album = albumRepo.findOne(id);
        Artist artist = album.getArtist();
        ass.removeAlbumsFromTags(album);
        aas.removeAlbumFromArtist(album, artist);
        albumRepo.delete(album);
           
        return "redirect:/artists/" + artist.getId();
    }
    
    @RequestMapping(value = "/albums/{albumId}", method=RequestMethod.POST)
    public String addTagsToAlbum(@PathVariable Long albumId, @RequestParam Long id){
        ass.addTagsforAlbums(albumId, id);
        return "redirect:/albums/" + albumId; 
    }
    
    @RequestMapping(value = "/albums/{id}/comment", method=RequestMethod.POST)
    public String addComment(@RequestParam String content, @PathVariable String id){
        acs.commentAlbum(content, Long.parseLong(id));
        return "redirect:/albums/" + id; 
    }
   
    
   
}
