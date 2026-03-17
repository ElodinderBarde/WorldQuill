package ch.Elodin.RealmQuill.repository.npcinfo;

import ch.Elodin.RealmQuill.model.npcinfo.Background;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackgroundRepository extends JpaRepository<Background, Integer> {
    Optional<Background> findByName(String name);
}



