
package MusicLibrary.repository;

import MusicLibrary.domain.StyleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleTagRepository extends JpaRepository<StyleTag, Long>{
    StyleTag findByName(String name);
}
