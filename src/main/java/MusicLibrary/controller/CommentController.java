
package MusicLibrary.controller;

import MusicLibrary.service.AlbumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {
    
    @Autowired
    private AlbumCommentService acs;
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable Long id){
        return "redirect:/albums/" + acs.removeComment(id);
    }
}
