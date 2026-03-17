package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Family;
import ch.Elodin.RealmQuill.repository.npcinfo.FamilyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Family")
public class FamilyController extends GenericController<Family, Integer> {

    public FamilyController(FamilyRepository repository) {
        super(repository);
    }
}



