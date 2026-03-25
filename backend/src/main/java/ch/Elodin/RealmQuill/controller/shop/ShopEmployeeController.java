package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopEmployee;
import ch.Elodin.RealmQuill.repository.shop.ShopEmployeeRepository;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/ShopEmployee")
public class ShopEmployeeController extends GenericController<ShopEmployee, Integer> {
    public ShopEmployeeController(ShopEmployeeRepository repository) {
        super(repository);
    }


}




