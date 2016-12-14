
package MusicLibrary.service;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import MusicLibrary.repository.StyleTagRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArtistAlbumService {
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private AlbumStyleTagService ass;
     
    public void addAlbumToArtist(Album album, Long artistId) {
        Artist artist = artistRepo.findOne(artistId);
        artist.getAlbums().add(album);
        artistRepo.save(artist);
    }
    
    public void removeAlbumFromArtist(Album album, Artist artist) {
        artist.getAlbums().remove(album);
    }
    
    @Transactional
    public void deleteArtistAndDeleteAlbums(Long id){
        List<Album> list = artistRepo.findOne(id).getAlbums();
        List<Long> albumIds = new ArrayList<>();
        
        for (Album a : list) {
            ass.removeAlbumsFromTags(a);
            albumIds.add(a.getId());
        }
        list.clear();
       
        for (Long i : albumIds) {
            albumRepo.delete(i);
        }
        artistRepo.delete(id);
    }
            
    
    
    
}
