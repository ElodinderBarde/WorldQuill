package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NpcClassRepository extends JpaRepository<NpcClass, Integer> {
    Optional<NpcClass> findByClassname(String classname);
}
