
package MusicLibrary.controller;

import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.StyleTagRepository;
import MusicLibrary.service.AlbumStyleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StyleTagController {
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    @Autowired
    private AlbumStyleTagService ass;
    
    @RequestMapping("/styletags")
    public String getStyleTags(Model model) {
        model.addAttribute("styletags", styleTagRepo.findAll());
        return "styleTagIndex";
    }
    
    @RequestMapping(value = "/styletags/{id}")
    public String getStyleTag(Model model, @PathVariable Long id) {
        model.addAttribute("styletag", styleTagRepo.findOne(id));
        return "styleTagPage";
    }
    
    @RequestMapping(value = "/styletags/new", method = RequestMethod.POST)
    public String addNewTag(@RequestParam String name, @RequestParam Long albumId) {
        if (styleTagRepo.findByName(name) == null) {
            StyleTag tag = new StyleTag();
            tag.setName(name);
            styleTagRepo.save(tag);
        }
        return "redirect:/albums/" + albumId;
    }
   
    @Secured("ADMIN")
    @RequestMapping(value = "/styletags/{id}", method = RequestMethod.PUT)
    public String removeTagFromAlbum(@PathVariable Long id, @RequestParam Long albumId) {
        ass.removeTagFromAlbum(id, albumId);
        return "redirect:/albums/" + albumId;
    }
}
