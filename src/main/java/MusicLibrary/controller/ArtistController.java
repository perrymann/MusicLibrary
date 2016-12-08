
package MusicLibrary.controller;

import MusicLibrary.domain.Artist;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.service.ArtistAlbumService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArtistController {
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private ArtistAlbumService aas;
    
    @RequestMapping("/artists")
    public String index(Model model) {
        model.addAttribute("artists", artistRepo.findAll());
        return "artistIndex";
    }
    
    @RequestMapping(value="/artists/new", method=RequestMethod.POST)
    public String addArtist(@RequestParam String artistName){
        Artist x = new Artist();
        x.setName(artistName);
        artistRepo.save(x);
        return "redirect:/artists";
    }
    
    @RequestMapping(value="/artists/{id}", method=RequestMethod.GET)
    public String getArtist(Model model, @PathVariable Long id) {
        model.addAttribute("artist", artistRepo.findOne(id));
        model.addAttribute("albumList", artistRepo.findOne(id).getAlbums());
        return "artistPage";
    }
    
    @RequestMapping(value="/artists", method=RequestMethod.POST)
    public String findArtist(@RequestParam String artistName) {
        Artist a = artistRepo.findByName(artistName);
        String ret = "redirect:/artists/"; 
        if (a != null) ret += a.getId();
        return ret;
    }
    
    @RequestMapping(value="/artists/{id}", method=RequestMethod.DELETE)
    public String deleteArtist(@PathVariable Long id){
        aas.deleteArtistAndDeleteAlbums(id);
        return "redirect:/artists";
    }
    
    
}
