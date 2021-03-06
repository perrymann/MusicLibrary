
package MusicLibrary.controller;

import MusicLibrary.domain.Artist;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.service.ArtistAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
        if (artistRepo.findByName(artistName) == null && !artistName.isEmpty()) {
            Artist x = new Artist();
            x.setName(artistName);
            artistRepo.save(x);
        }
        return "redirect:/artists";
    }
    
    @RequestMapping(value="/artists/{id}", method=RequestMethod.GET)
    public String getArtist(Model model, @PathVariable Long id) {
        model.addAttribute("artist", artistRepo.findOne(id));
        model.addAttribute("albumList", artistRepo.findOne(id).getAlbums());
        return "artistPage";
    }
    
    @Secured("ADMIN")
    @RequestMapping(value="/artists/{id}", method=RequestMethod.DELETE)
    public String deleteArtist(@PathVariable Long id){
        aas.deleteArtistAndDeleteAlbums(id);
        return "redirect:/artists";
    }
    
    
}
