package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.dto.ClanDTO;
import ch.Elodin.RealmQuill.dto.ClanNotesUpdateDTO;
import ch.Elodin.RealmQuill.mapper.ClanMapper;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/clan")
public class ClanController {

    private final ClanRepository clanRepository;

    public ClanController(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    @GetMapping
    public List<ClanDTO> getAllClans() {
        return clanRepository.findAll()
                .stream()
                .map(ClanMapper::toClanDTO)
                .toList();
    }

    @GetMapping("/ClanNames")
    public List<String> getAllClanNames() {
        return (List<String>) clanRepository.findAllClanNames();
    }

    @GetMapping("/Familienclans")
    public List<ClanDTO> getFamilienclans() {
        return clanRepository.findAllFamilienclans()
                .stream()
                .map(ClanMapper::toClanDTO)
                .toList();
    }

    @GetMapping("/NonFamilienclans")
    public List<ClanDTO> getNonFamilienclans() {
        List<ClanDTO> list = clanRepository.findAllNonFamilienclans()
                .stream()
                .map(ClanMapper::toClanDTO)
                .toList();
        return list;
    }

    @PostMapping("/createClan")
    public ClanDTO createClan(@RequestBody Clan clan) {
        Clan saved = clanRepository.save(clan);
        return ClanMapper.toClanDTO(saved);
    }

    @GetMapping("/GetClanNotes/{clanId}")
    public String getClanNotes(@PathVariable int clanId) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found with id: " + clanId));
        return clan.getClanNotes();
    }

    @PutMapping("/updateNotes/{clanId}")
    public ClanDTO updateClanNotes(
            @PathVariable int clanId,
            @RequestBody ClanNotesUpdateDTO dto
    ) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found: " + clanId));

        clan.setClanNotes(dto.getClanNotes());
        clanRepository.save(clan);

        return ClanMapper.toClanDTO(clan);
    }

    @GetMapping("/{id}")
    public ClanDTO getClanById(@PathVariable int id) {
        return ClanMapper.toClanDTO(
                clanRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Clan not found: " + id))
        );
    }

}



