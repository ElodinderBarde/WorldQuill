package ch.Elodin.RealmQuill.controller.world;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.world.City;
import ch.Elodin.RealmQuill.repository.world.CityRepository;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5137")

@RestController
@RequestMapping("/api/City")
public class CityController extends GenericController<City, Integer> {
    public CityController(CityRepository repository) {
        super(repository);
    }
}




