package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Hairstyle;
import ch.Elodin.RealmQuill.repository.npcinfo.HairstyleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Hairstyle")
public class HairstyleController extends GenericController<Hairstyle, Integer> {
    public HairstyleController(HairstyleRepository repository) {
        super(repository);
    }
}




