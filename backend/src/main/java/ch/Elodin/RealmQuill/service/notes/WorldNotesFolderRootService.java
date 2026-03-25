package ch.Elodin.RealmQuill.service.notes;



import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesFolderRepository;
import ch.Elodin.RealmQuill.service.Login.UserBootstrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WorldNotesFolderRootService {

    private final WorldNotesFolderRepository folderRepository;
    private final UserBootstrapService userBootstrapService;

    @Transactional
    public WorldNotesFolder ensureRootFolder(AppUser user) {
        List<WorldNotesFolder> roots = folderRepository.findByUserAndParentFolderIsNull(user);

        if (!roots.isEmpty()) {
            return roots.get(0);
        }
    userBootstrapService.initializeDefaultData(user);
        WorldNotesFolder root = new WorldNotesFolder();
        root.setUser(user);
        root.setCreatedAt(Instant.now());
        root.setUpdatedAt(Instant.now());
        root.setVersion(0L);

        return folderRepository.save(root);
    }
}