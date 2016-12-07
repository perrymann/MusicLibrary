
package MusicLibrary.service;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.Artist;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArtistAlbumService {
    
    @Autowired
    private ArtistRepository artistRepo;
    
    public void addAlbumToArtist(Album album, Long artistId) {
        Artist artist = artistRepo.findOne(artistId);
        artist.getAlbums().add(album);
        artistRepo.save(artist);
    }
    
    
    
}
