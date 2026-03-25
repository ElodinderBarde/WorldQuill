package ch.Elodin.RealmQuill.repository.ruf;
import ch.Elodin.RealmQuill.model.ruf.ReputationConflict;
import ch.Elodin.RealmQuill.model.ruf.ReputationConflictKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReputationConflictRepository extends JpaRepository<ReputationConflict, ReputationConflictKey> {
}
