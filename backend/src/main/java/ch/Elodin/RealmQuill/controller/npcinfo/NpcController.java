package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.dto.NpcFilterRequest;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import ch.Elodin.RealmQuill.repository.npcinfo.RaceRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.SubclassRepository;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.NpcService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/npcs")
@RequiredArgsConstructor
public class NpcController {

    private static final Logger LOG = LoggerFactory.getLogger(NpcController.class);
    private final NpcService npcService;
    private final RaceRepository raceRepository;
    private final SubclassRepository subclassRepository;
    private final NpcRepository npcRepository;
    private final ClanRepository clanRepository;

    @GetMapping("/dto")
    public List<NpcReadDTO> getAllAsDTO() { return npcService.getAllNpcs(); }

    @GetMapping("/dto/filter")
    public List<NpcReadDTO> getFilteredNpcs(NpcFilterRequest filter) {
        return npcService.getFilteredNpcs(filter);
    }

    @GetMapping("/{id}")
    public NpcReadDTO getNpcById(@PathVariable int id) { return npcService.getNpcById(id); }

    @GetMapping("/byClan/{clanId}")
    public List<NpcReadDTO> getNpcByClanId(@PathVariable int clanId) {
        return npcService.getNpcsByClanId(clanId);
    }

    @GetMapping("/byCampaign/{campaignId}")
    public List<NpcReadDTO> getNpcByCampaignId(@PathVariable int campaignId) {
        return npcService.getNpcsByCampaignId(campaignId);
    }

    @GetMapping("/races/names")
    public List<String> getAllRaceNames() {
        return raceRepository.findAll().stream().map(r -> r.getRacename()).distinct().toList();
    }

    @GetMapping("/subclasses/names")
    public List<String> getAllSubclassNames() {
        return subclassRepository.findAll().stream().map(Subclass::getSubclassname).distinct().toList();
    }

    @GetMapping("/subclasses/names/byClass")
    public List<String> getSubclassNamesByClass(@RequestParam(required = false) String npcClass) {
        if (npcClass == null || npcClass.isBlank()) return Collections.emptyList();
        return subclassRepository.findByClassname(npcClass).stream()
                .map(Subclass::getSubclassname).distinct().toList();
    }

    @GetMapping("/subclasses/byClass/{classId}")
    public List<Subclass> getSubclassesByClassId(@PathVariable int classId) {
        return subclassRepository.findByClassId(classId);
    }

    @GetMapping("/clans/names")
    public List<String> getAllClanNames() {
        return clanRepository.findAll().stream().map(Clan::getClan).distinct().toList();
    }

    @GetMapping("/clanpositions/names")
    public List<String> getAllClanPositionNames() {
        return npcRepository.findAll().stream()
                .map(Npc::getClan_position)
                .filter(pos -> pos != null && !pos.isEmpty())
                .distinct().toList();
    }

    @GetMapping("/classes/names")
    public List<String> getAllClassNames() {
        return npcRepository.findAll().stream()
                .map(npc -> npc.getNpcClass() != null ? npc.getNpcClass().getClassname() : null)
                .filter(name -> name != null && !name.isEmpty())
                .distinct().toList();
    }

    @PostMapping
    public ResponseEntity<?> createNpc(@RequestBody NpcWriteDTO dto) {
        try {
            npcService.createNpc(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error("Fehler beim Erstellen des NPCs: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Fehler: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNpcNotes(@PathVariable int id, @RequestBody NpcReadDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + id));
        npc.setNotes(dto.getNotes());
        npcRepository.save(npc);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateClanPosition/{id}")
    public ResponseEntity<?> updateNpcClanPosition(@PathVariable int id, @RequestBody NpcReadDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + id));
        npc.setClan_position(dto.getClanPosition());
        npcRepository.save(npc);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateClan/{id}")
    public ResponseEntity<?> updateNpcClan(@PathVariable int id, @RequestBody NpcReadDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + id));
        Clan clan = clanRepository.findByClan(dto.getClan())
                .orElseThrow(() -> new RuntimeException("Clan nicht gefunden: " + dto.getClan()));
        npc.setClan(clan);
        npcRepository.save(npc);
        return ResponseEntity.ok().build();
    }
}
