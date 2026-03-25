package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.NpcGeneratorService;
import ch.Elodin.RealmQuill.service.NpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/npc-generator")
@RequiredArgsConstructor
public class NpcGeneratorController {
    private final NpcGeneratorService generatorService;
    private final NpcRepository npcRepository;
    private final NpcService npcService;

    // Gibt NpcReadDTO statt roher Entity zurück → verhindert LazyInitializationException
    @PostMapping("/generate")
    public ResponseEntity<NpcReadDTO> generateAndSave() {
        Npc saved = npcRepository.save(generatorService.generateRandomNpc());
        return ResponseEntity.ok(npcService.getNpcById(saved.getNpc_ID()));
    }
}
