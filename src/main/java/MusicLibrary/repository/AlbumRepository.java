package MusicLibrary.repository;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>{
    List<Album> findByArtist(Artist artist);
    Album findByTitle(String title);
}
