package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.NpcGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/npc-generator")
public class NpcGeneratorController {

    private final NpcGeneratorService generatorService;
    private final NpcRepository npcRepository;

    public NpcGeneratorController(NpcGeneratorService generatorService, NpcRepository npcRepository) {
        this.generatorService = generatorService;
        this.npcRepository = npcRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<Npc> generateAndSave() {
        Npc npc = generatorService.generateRandomNpc();
        npcRepository.save(npc);
        return ResponseEntity.ok(npc);
    }



}



