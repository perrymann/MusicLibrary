package MusicLibrary.repository;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>{
    
}
