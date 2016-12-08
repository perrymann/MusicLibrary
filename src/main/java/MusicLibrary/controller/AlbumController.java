
package MusicLibrary.controller;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.service.ArtistAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ArtistAlbumService aas;
    
    @RequestMapping("/albums")
    public String index(Model model) {
        model.addAttribute("albums", albumRepo.findAll());
        return "albumIndex";
    }
    
    @RequestMapping("/albums/{id}")
    public String getAlbum(Model model, @PathVariable Long id) {
        model.addAttribute("album", albumRepo.findOne(id));
        return "albumPage";
    }
     
    @RequestMapping(value="/artists/{id}", method=RequestMethod.POST)
    public String addAlbum(@PathVariable Long id, @RequestParam String title, @RequestParam String year, @RequestParam String label){
        Album x = new Album();
        x.setTitle(title);
        x.setReleasedIn(Integer.parseInt(year));
        x.setLabel(label);
        x.setArtist(id);
        albumRepo.save(x);
        aas.addAlbumToArtist(x, id);
        return "redirect:/artists/" + id;
    }
    
    @RequestMapping(value="/albums/{id}", method=RequestMethod.DELETE)
    public String deleteAlbum(@PathVariable Long id) {
        boolean ok = false;
        Album album = albumRepo.findOne(id);
        Artist artist = artistRepo.findOne(album.getArtist());
        aas.removeAlbumFromArtist(album, artist);
        albumRepo.delete(album);
           
        return "redirect:/artists";
    }
    
   
}
