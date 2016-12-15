
package MusicLibrary.service;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Comment;
import MusicLibrary.repository.AccountRepository;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.CommentRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AlbumCommentService {
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    
    public void commentAlbum(String content, Long id) {
        Comment comment = new Comment();
        Album album = albumRepo.findOne(id);
        comment.setContent(content);
        comment.setAlbum(album);
        comment.setDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setAccount(accountRepo.findByUsername(auth.getName()));
        album.setComment(comment);
        albumRepo.save(album);
        commentRepo.save(comment);
    }
    
    public Long removeComment(Long id) {
        Comment comment = commentRepo.findOne(id);
        Album album = comment.getAlbum();
        album.getComments().remove(comment);
        albumRepo.save(album);
        commentRepo.delete(comment);
        return album.getId();
    }
    
    
}
