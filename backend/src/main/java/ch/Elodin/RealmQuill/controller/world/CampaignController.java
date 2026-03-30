package ch.Elodin.RealmQuill.controller.world;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import ch.Elodin.RealmQuill.repository.world.CampaignRepository;
import ch.Elodin.RealmQuill.service.notes.NotesSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignRepository campaignRepository;
    private final NotesSyncService notesSyncService;
    private final AppUserRepository userRepo;

    @GetMapping
    public List<Campaign> getAll() { return campaignRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getById(@PathVariable int id) {
        return campaignRepository.findById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Campaign> create(@RequestBody Campaign campaign) {
        Campaign saved = campaignRepository.save(campaign);

        // Folder-Grundstruktur für neue Kampagne anlegen
        AppUser user = userRepo.findByUsername("Elodin")
                .orElseThrow(() -> new RuntimeException("User nicht gefunden"));

        WorldNotesCategory category = notesSyncService.getOrCreateCategory(user);
        WorldNotesFolder root = notesSyncService.getOrCreateFolder(
                "Dein Hauptordner", user, category, null);

        notesSyncService.syncCampaignStructure(saved, user, root, category);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> update(@PathVariable int id, @RequestBody Campaign dto) {
        Campaign c = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kampagne nicht gefunden: " + id));
        c.setCampaignName(dto.getCampaignName());
        return ResponseEntity.ok(campaignRepository.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        campaignRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}