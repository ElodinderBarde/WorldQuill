package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.dto.NpcFilterRequest;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import ch.Elodin.RealmQuill.repository.npcinfo.RaceRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.SubclassRepository;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.NpcService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/npcs")
public class NpcController {

    private final NpcService npcService;
    private final RaceRepository raceRepository;
    private final SubclassRepository subclassRepository;
    private final NpcRepository npcRepository;
    private final ClanRepository clanRepository;
    private static final Logger LOG = LoggerFactory.getLogger(NpcController.class);


    public NpcController(NpcService npcService,
                         RaceRepository raceRepository,
                         SubclassRepository subclassRepository,
                         NpcRepository npcRepository,
                         ClanRepository clanRepository
    ) {
        this.npcService = npcService;
        this.raceRepository = raceRepository;
        this.subclassRepository = subclassRepository;
        this.npcRepository = npcRepository;
        this.clanRepository = clanRepository;
    }


    @GetMapping("/dto")
    public List<NpcReadDTO> getAllAsDTO() {
        return npcService.getAllNpcs();
    }

    @GetMapping("/dto/filter")
    public List<NpcReadDTO> getFilteredNpcs(NpcFilterRequest filter) {
        return npcService.getFilteredNpcs(filter);
    }

    @GetMapping("/races/names")
    public List<String> getAllRaceNames() {
        return raceRepository.findAll().stream()
                .map(r -> r.getRacename())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/subclasses/names")
    public List<String> getAllSubclassNames() {
        return subclassRepository.findAll().stream()
                .map(s -> s.getSubclassname())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/clans/names")
    public List<String> getAllClanNames() {
        return clanRepository.findAll().stream()
                .map(c -> c.getClan())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/clanpositions/names")
    public List<String> getAllClanPositionNames() {
        return npcRepository.findAll().stream()
                .map(Npc::getClan_position)
                .filter(pos -> pos != null && !pos.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/classes/names")
    public List<String> getAllClassNames() {
        return npcRepository.findAll().stream()
                .map(npc -> npc.getNpcClass() != null ? npc.getNpcClass().getClassname() : null)
                .filter(name -> name != null && !name.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/subclasses/names/byClass")
    public List<String> getSubclassNamesByClass(@RequestParam(required = false) String npcClass) {
        if (npcClass == null || npcClass.isBlank()) return Collections.emptyList();
        return subclassRepository.findByClassname(npcClass).stream()
                .map(Subclass::getSubclassname)
                .distinct()
                .toList();
    }

    @GetMapping("/Subclasses/byClass/{classId}")
    public List<Subclass> getSubclassesByClassId(@PathVariable int classId) {
        return subclassRepository.findByClassId(classId);

    }


    @PutMapping("/{id}")
    public void updateNpcNotes(@PathVariable int id, @RequestBody NpcReadDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden mit ID: " + id));

        npc.setNotes(dto.getNotes());
        npcRepository.save(npc);
    }

    @GetMapping("/{id}")
    public NpcReadDTO getNpcById(@PathVariable int id) {
        return npcService.getNpcById(id);
    }

    @PostMapping
    public ResponseEntity<?> createNpc(@RequestBody NpcWriteDTO dto) {
        try {
            npcService.createNpc(dto);
            LOG.info("Empfangener NPC DTO: {}", dto);
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            e.printStackTrace(); // fÃƒÂ¼r Debug-Zwecke
            LOG.info("Empfangener NPC DTO: {}", dto);

            return ResponseEntity.badRequest().body("Fehler beim Erstellen des NPCs: " + e.getMessage());
        }
    }

    @RestController
    @RequestMapping("/api/Symbol")
    public class SymbolController {

        @GetMapping
        public EnumSymbol[] getAllSymbols() {
            return EnumSymbol.values();
        }
    }


    @GetMapping("/byClan/{clanId}")
    public List<NpcReadDTO> getNpcByClanId(@PathVariable int clanId) {
        return npcService.getNpcsByClanId(clanId);
    }

    @PutMapping("UpdateClanPosition/{id}")
    public void updateNpcClanPosition(@PathVariable int id, @RequestBody NpcReadDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden mit ID: " + id));

        npc.setClan_position(dto.getClanPosition());
        npcRepository.save(npc);
    }

    @PutMapping("UpdateClan/{id}")
    public void updateNpcClan(@PathVariable int id, @RequestBody NpcReadDTO dto) {

        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden mit ID: " + id));

        // Clan anhand des Namens laden
        Clan clan = clanRepository.findByClan(dto.getClan())
                .orElseThrow(() -> new RuntimeException("Clan nicht gefunden: " + dto.getClan()));

        npc.setClan(clan);
        npcRepository.save(npc);
    }

}


