
package MusicLibrary.service;

import MusicLibrary.domain.Album;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import java.util.ArrayList;
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
    
    @Autowired
    private AlbumCommentService acs;
    
    @Transactional
    public void deleteArtistAndDeleteAlbums(Long id){
        List<Album> list = artistRepo.findOne(id).getAlbums();
        List<Long> albumIds = new ArrayList<>();
        
        for (Album a : list) {
            acs.removeComments(a.getComments());
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
