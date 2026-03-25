package ch.Elodin.RealmQuill.repository.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.model.notes.WorldNotesNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldNotesNoteRepository extends JpaRepository<WorldNotesNote, Long> {



        List<WorldNotesNote> findByUserId(Long userId);
        Optional<WorldNotesNote> findByIdAndUser(Long id, AppUser user);

        Optional<WorldNotesNote> findByIdAndUserId(Long id, Long userId);

        List<WorldNotesNote> findByFolderAndUserId(WorldNotesFolder folder, Long userId);

        List<WorldNotesNote> findAllByUser(AppUser user);

        Optional<WorldNotesNote> findByTitleAndUser(String title, AppUser user);

        List<WorldNotesNote> findByFolderIdAndUserId(Long folderId, Long userId);

}
