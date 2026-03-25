package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopType;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/ShopType")
public class ShopTypeController extends GenericController<ShopType, Integer> {
    public ShopTypeController(ShopTypeRepository repository) {
        super(repository);
    }
}




