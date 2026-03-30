package ch.Elodin.RealmQuill.config;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.model.enums.RoleEnum;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.model.notes.WorldNotesNote;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.City;
import ch.Elodin.RealmQuill.model.world.Village;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesCategoryRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesFolderRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesNoteRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import ch.Elodin.RealmQuill.repository.world.*;
import ch.Elodin.RealmQuill.service.notes.NotesSyncService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final WorldNotesCategoryRepository categoryRepo;
    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesNoteRepository noteRepo;
    private final PasswordEncoder passwordEncoder;
    private final NotesSyncService notesSyncService;
    private final CampaignRepository campaignRepo;



    // ── HELPER ────────────────────────────────────────────────────────────────
// Ordner holen oder erstellen
    private WorldNotesFolder getOrCreateFolder(String name, AppUser user,
                                               WorldNotesCategory category, WorldNotesFolder parent) {

        return folderRepo.findByNameAndUserAndParentFolder(name, user, parent).orElseGet(() -> {
            WorldNotesFolder f = new WorldNotesFolder();
            f.setName(name);
            f.setUser(user);
            f.setCategory(category);
            f.setParentFolder(parent);
            return folderRepo.save(f);
        });
    }

    // Note holen oder erstellen
    private void syncNote(String title, String content, WorldNotesFolder folder,
                          AppUser user, WorldNotesCategory category) {

        noteRepo.findByTitleAndUser(title, user).ifPresentOrElse(
                existing -> {
                    if (existing.getFolder() == null) {
                        existing.setFolder(folder);
                        noteRepo.save(existing);
                    }
                },
                () -> {
                    WorldNotesNote n = new WorldNotesNote();
                    n.setTitle(title);
                    n.setContent(content != null ? content : "");
                    n.setFolder(folder);
                    n.setUser(user);
                    n.setCategory(category);
                    noteRepo.save(n);
                }
        );
    }


    @Override
    public void run(String... args) {

        // DEFAULT USER ---------------------------------------------------------
        AppUser user = userRepo.findByUsername("Elodin").orElse(null);
        if (user == null) {
            user = new AppUser();
            user.setUsername("Elodin");
            user.setEmail("Elodin@local");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setRole(RoleEnum.ADMIN);
            userRepo.save(user);
            System.out.println("✔ Default-User 'Elodin' erstellt");
        }

        // DEFAULT CATEGORY -----------------------------------------------------
        WorldNotesCategory category =
                categoryRepo.findByNameAndUser("Allgemein", user).orElse(null);

        if (category == null) {
            category = new WorldNotesCategory();
            category.setName("Allgemein");
            category.setUser(user);
            categoryRepo.save(category);
            System.out.println(" Kategorie 'Allgemein' erstellt");
        }

        notesSyncService.fullSync(user);


        // DEFAULT WELCOME NOTE -------------------------------------------------
        boolean welcomeExists = noteRepo.findByTitleAndUser("Willkommen in NotesHandler", user).isPresent();
        if (!welcomeExists) {
            WorldNotesFolder root = notesSyncService.getOrCreateFolder(
                    "Dein Hauptordner", user, notesSyncService.getOrCreateCategory(user), null);

            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Willkommen in NotesHandler");
            note.setContent("""
Dies ist deine erste Notiz.

Die Plattform unterstützt **Markdown**.
Viel Spaß beim Schreiben!

# Hello There
## Das ist eine Überschrift
Verwalte deine Notizen einfach mit Drag and Drop, sowie dem Kontextmenü bei Rechtsklick auf einen Ordner oder Notiz.

---
Markdown unterstützt einige verschiedene Funktionen

### Codeblöcke
```cpp
std::cout << "Ich kann hier auch Codeblöcke verwenden";
std::endl;
// ich lasse diesen Code nach 10 zählen
for (int i = 0; i < 10; ++i) {
    std::cout << "\\nIch kann auf " << i << " zählen!";
}
```
---
### Optische bearbeitung

Einen Codeblock hier zu erkennen ist jedoch nicht ganz einfach
aber ich kann auch **Fett** aber auch *kursiv*.

---
Für weitere  Syntaxhilfe → www.markdown.de
""");
            note.setFolder(root);
            note.setUser(user);
            noteRepo.save(note);

            System.out.println(" Begrüßungsnotiz erstellt");
        }
    }
}
