package ch.Elodin.RealmQuill.controller.world;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.world.Village;
import ch.Elodin.RealmQuill.repository.world.VillageRepository;
import org.springframework.web.bind.annotation.*;




@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Village")
public class VillageController extends GenericController<Village, Integer> {
    public VillageController(VillageRepository repository) {
        super(repository);
    }
}




