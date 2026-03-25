package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.dto.QuestCreateDTO;
import ch.Elodin.RealmQuill.dto.QuestDto;
import ch.Elodin.RealmQuill.mapper.QuestMapper;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.repository.world.QuestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController @RequestMapping("/api/quest")
public class QuestController extends GenericController<Quest, Integer> {
    private final QuestRepository questRepository;
    public QuestController(QuestRepository repository) {
        super(repository);
        this.questRepository = repository;
    }
    @GetMapping("/active")
    public ResponseEntity<List<QuestDto>> getActive() {
        return ResponseEntity.ok(questRepository.findByActiveTrue().stream().map(QuestMapper::toDto).toList());
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuestCreateDTO dto) {
        Quest quest = QuestMapper.toEntity(dto);
        if (dto.getPreviousQuestId() != null)
            quest.setPreviousQuest(questRepository.findById(dto.getPreviousQuestId()).orElse(null));
        questRepository.save(quest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/notes")
    public ResponseEntity<?> updateNotes(@PathVariable int id, @RequestBody Map<String, String> body) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setNotes(body.get("notes"));
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable int id) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setStatus(EnumQuest.abgeschlossen);
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/incomplete")
    public ResponseEntity<?> incomplete(@PathVariable int id) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setStatus(EnumQuest.offen);
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/active")
    public ResponseEntity<Void> setActive(@PathVariable int id, @RequestBody Map<String, Boolean> body) {
        Quest q = questRepository.findById(id).orElseThrow();
        q.setActive(body.get("active"));
        questRepository.save(q);
        return ResponseEntity.ok().build();
    }
}