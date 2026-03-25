package ch.Elodin.RealmQuill.repository.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesCategoryRepository
        extends JpaRepository<WorldNotesCategory, Long> {

    List<WorldNotesCategory> findAllByUser(AppUser user);

    Optional<WorldNotesCategory> findByIdAndUser(Long id, AppUser user);

    Optional<WorldNotesCategory> findByNameAndUser(String name, AppUser user);
}
