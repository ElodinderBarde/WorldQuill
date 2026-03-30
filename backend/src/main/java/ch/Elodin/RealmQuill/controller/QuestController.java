package ch.Elodin.RealmQuill.controller;
import ch.Elodin.RealmQuill.dto.QuestCreateDTO;
import ch.Elodin.RealmQuill.dto.QuestDto;
import ch.Elodin.RealmQuill.mapper.QuestMapper;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.repository.world.CampaignRepository;
import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import ch.Elodin.RealmQuill.repository.world.QuestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5137")
@RestController @RequestMapping("/api/quest")
public class QuestController extends GenericController<Quest, Integer> {
    private static final int DEFAULT_CAMPAIGN_ID = 1;

    private final QuestRepository questRepository;
    private final LocationRepository locationRepository;
    private final CampaignRepository campaignRepository;

    public QuestController(QuestRepository repository, LocationRepository locationRepository, CampaignRepository campaignRepository) {
        super(repository);
        this.questRepository = repository;
        this.locationRepository = locationRepository;
        this.campaignRepository = campaignRepository;
    }

    @GetMapping("/all")
    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<List<QuestDto>> getActive() {
        return ResponseEntity.ok(questRepository.findByActiveTrue().stream().map(QuestMapper::toDto).toList());
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuestCreateDTO dto) {
        Quest quest = QuestMapper.toEntity(dto);
        int campaignId = dto.getCampaignId() != null ? dto.getCampaignId() : DEFAULT_CAMPAIGN_ID;
        quest.setCampaign(campaignRepository.findById(campaignId).orElse(null));
        if (dto.getQuestlocationId() > 0) {
            quest.setQuestlocation(locationRepository.findById(dto.getQuestlocationId()).orElse(null));
        }
        if (dto.getPreviousQuestId() != null)
            quest.setPreviousQuest(questRepository.findById(dto.getPreviousQuestId()).orElse(null));
        questRepository.save(quest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/notes")
    public ResponseEntity<?> updateNotes(@PathVariable("id") int id, @RequestBody Map<String, String> body) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setNotes(body.get("notes"));
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable("id") int id) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setStatus(EnumQuest.abgeschlossen);
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/incomplete")
    public ResponseEntity<?> incomplete(@PathVariable("id") int id) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setStatus(EnumQuest.offen);
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/active")
    public ResponseEntity<Void> setActive(@PathVariable("id") int id, @RequestBody Map<String, Boolean> body) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setActive(body.get("active"));
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
}