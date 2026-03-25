package ch.Elodin.RealmQuill.controller.npcinfo.family;
import ch.Elodin.RealmQuill.dto.FamilyRelationDTO;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.repository.npcinfo.Family.FamilyRelationRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.FamilyTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/family-tree")
@RequiredArgsConstructor
public class FamilyTreeController {
    private final FamilyTreeService familyTreeService;
    private final FamilyRelationRepository relationRepository;
    private final NpcRepository npcRepository;

    @PostMapping("/{npcId}/generate")
    public ResponseEntity<List<FamilyRelationDTO>> generateFamily(
            @PathVariable int npcId, @RequestParam int count) {
        if (count < 1 || count > 20) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(
                familyTreeService.generateFamilyFor(npcId, count)
                        .stream().map(FamilyRelationDTO::from).toList());
    }

    @GetMapping("/{npcId}")
    public ResponseEntity<List<FamilyRelationDTO>> getFamilyTree(@PathVariable int npcId) {
        Npc npc = npcRepository.findById(npcId)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + npcId));
        return ResponseEntity.ok(
                relationRepository.findBySourceNpc(npc)
                        .stream().map(FamilyRelationDTO::from).toList());
    }

    @DeleteMapping("/{npcId}/relations")
    public ResponseEntity<?> deleteAllRelations(@PathVariable int npcId) {
        Npc npc = npcRepository.findById(npcId)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + npcId));
        relationRepository.deleteAll(relationRepository.findBySourceNpc(npc));
        return ResponseEntity.ok().build();
    }
}
