package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import ch.Elodin.RealmQuill.repository.npcinfo.SubclassRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Subclass") // Basis-URL fÃ¼r diesen Controller
public class SubclassController extends GenericController<Subclass, Integer> {

    private final SubclassRepository subclassRepository;

    public SubclassController(SubclassRepository repository) {
        super(repository);
        this.subclassRepository = repository;
    }

    public record SubclassDTO(Integer id, String name) {}

    @GetMapping("/names/byClass")
    public List<SubclassDTO> getSubclassNamesByClass(@RequestParam String npcClass) {
        return subclassRepository.findByClassname(npcClass).stream()
                .map(sc -> new SubclassDTO(sc.getSubclass_ID(), sc.getSubclassname()))
                .toList();
    }
}



