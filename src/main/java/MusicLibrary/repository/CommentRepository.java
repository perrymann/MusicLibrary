
package MusicLibrary.repository;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByAlbum(Album album);
}
