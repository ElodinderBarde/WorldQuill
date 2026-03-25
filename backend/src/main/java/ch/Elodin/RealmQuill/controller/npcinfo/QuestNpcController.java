package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.dto.QuestNpcDto;
import ch.Elodin.RealmQuill.mapper.QuestNpcMapper;
import ch.Elodin.RealmQuill.repository.world.QuestNpcRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/questnpc")
@RequiredArgsConstructor
public class QuestNpcController {
    private final QuestNpcRepository questNpcRepository;

    @GetMapping
    public List<QuestNpcDto> getAll() {
        return questNpcRepository.findAll().stream().map(QuestNpcMapper::toDto).toList();
    }

    @GetMapping("/by-quest/{id}")
    public List<QuestNpcDto> getByQuest(@PathVariable int id) {
        return questNpcRepository.findByQuest_QuestID(id).stream().map(QuestNpcMapper::toDto).toList();
    }

    @GetMapping("/by-npc/{id}")
    public List<QuestNpcDto> getByNpc(@PathVariable int id) {
        return questNpcRepository.findByNpcId(id).stream().map(QuestNpcMapper::toDto).toList();
    }
}
