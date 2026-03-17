package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.dto.QuestCreateDTO;
import ch.Elodin.RealmQuill.dto.QuestDto;
import ch.Elodin.RealmQuill.mapper.QuestMapper;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import ch.Elodin.RealmQuill.repository.world.QuestRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/Quest")
public class QuestController extends GenericController<Quest, Integer> {

    private final QuestRepository questRepository;
    private final LocationRepository locationRepository;

    public QuestController(QuestRepository repository, LocationRepository locationRepository) {
        super(repository);
        this.questRepository = repository;
        this.locationRepository = locationRepository;
    }

    @PutMapping("/{id}/notes")
    public ResponseEntity<?> updateNotes(@PathVariable int id, @RequestBody Map<String, String> body) {
        Optional<Quest> optionalQuest = questRepository.findById(id);
        if (optionalQuest.isEmpty()) return ResponseEntity.notFound().build();

        Quest quest = optionalQuest.get();
        quest.setNotes(body.get("notes"));
        questRepository.save(quest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeQuest(@PathVariable int id) {
        Optional<Quest> optionalQuest = repository.findById(id);
        if (optionalQuest.isEmpty()) return ResponseEntity.notFound().build();

        Quest quest = optionalQuest.get();
        quest.setStatus(EnumQuest.valueOf("abgeschlossen"));
        repository.save(quest);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/incomplete")
    public ResponseEntity<?> incompleteQuest(@PathVariable int id) {
        Optional<Quest> optionalQuest = repository.findById(id);
        if (optionalQuest.isEmpty()) return ResponseEntity.notFound().build();

        Quest quest = optionalQuest.get();
        quest.setStatus(EnumQuest.valueOf("offen"));
        repository.save(quest);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/create")
    public ResponseEntity<?> createQuest(@RequestBody QuestCreateDTO dto) {
        Quest quest = QuestMapper.toEntity(dto);

        // previousQuest setzen, wenn vorhanden
        if (dto.getPreviousQuestId() != null) {
            quest.setPreviousQuest(questRepository.findById(dto.getPreviousQuestId()).orElse(null));
        }

        questRepository.save(quest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<QuestDto>> getActiveQuests() {
        List<QuestDto> activeQuests = questRepository.findByActiveTrue()
                .stream()
                .map(QuestMapper::toDto)
                .toList();

        return ResponseEntity.ok(activeQuests);
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<Void> setActive(
            @PathVariable int id,
            @RequestBody Map<String, Boolean> body
    ) {
        Quest quest = questRepository.findById(id)
                .orElseThrow();

        quest.setActive(body.get("active"));
        questRepository.save(quest);

        return ResponseEntity.ok().build();
    }

}


