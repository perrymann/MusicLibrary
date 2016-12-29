
package MusicLibrary.service;

import MusicLibrary.domain.Album;
import MusicLibrary.domain.StyleTag;
import MusicLibrary.repository.AlbumRepository;
import MusicLibrary.repository.StyleTagRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumStyleTagService {
    
    @Autowired
    private AlbumRepository albumRepo;
    
    @Autowired
    private StyleTagRepository styleTagRepo;
    
    // Adds a tag for a certain album
    
    @Transactional
    public void addTagsforAlbums(Long albumId, Long styleTagId) {
        Album album = albumRepo.findOne(albumId);
        StyleTag styleTag = styleTagRepo.findOne(styleTagId);
        album.getTags().add(styleTag);
        styleTag.getAlbums().add(album);
    }
    
    // Removes the references form the tags, if a certain album is deleted
    
    public void removeAlbumsFromTags(Album album) {
        for (StyleTag t : styleTagRepo.findAll()) {
            t.getAlbums().remove(album);
            styleTagRepo.save(t);
        }
    }
    
    // Removes a single tag from a certain album
    
    public void removeTagFromAlbum(Long tagId, Long albumId) {
        StyleTag tag = styleTagRepo.findOne(tagId);
        Album album = albumRepo.findOne(albumId);
        album.getTags().remove(tag);
        albumRepo.save(album);
        tag.getAlbums().remove(album);
        styleTagRepo.save(tag);
    }
}
