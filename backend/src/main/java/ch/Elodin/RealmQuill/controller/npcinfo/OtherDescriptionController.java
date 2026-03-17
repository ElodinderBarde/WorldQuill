package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.OtherDescription;
import ch.Elodin.RealmQuill.repository.npcinfo.OtherDescriptionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/OtherDescription")
public class OtherDescriptionController extends GenericController<OtherDescription, Integer> {
    public OtherDescriptionController(OtherDescriptionRepository repository) {
        super(repository);
    }
}




