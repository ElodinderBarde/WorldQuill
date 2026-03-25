package ch.Elodin.RealmQuill.repository.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesLink;
import ch.Elodin.RealmQuill.model.notes.WorldNotesNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesLinkRepository extends JpaRepository<WorldNotesLink, Long> {

    List<WorldNotesLink> findAllByNoteAndNote_User(WorldNotesNote note, AppUser user);

    Optional<WorldNotesLink> findByIdAndNote_User(Long id, AppUser user);
}
