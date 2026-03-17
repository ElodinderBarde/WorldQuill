package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Personality;
import ch.Elodin.RealmQuill.repository.npcinfo.PersonalityRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Personality")
public class PersonalityController extends GenericController<Personality, Integer> {
    public PersonalityController(PersonalityRepository repository) {
        super(repository);
    }
}




