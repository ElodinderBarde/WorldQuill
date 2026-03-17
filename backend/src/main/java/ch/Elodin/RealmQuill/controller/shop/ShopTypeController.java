package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopType;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ShopType")
public class ShopTypeController extends GenericController<ShopType, Integer> {
    public ShopTypeController(ShopTypeRepository repository) {
        super(repository);
    }
}




