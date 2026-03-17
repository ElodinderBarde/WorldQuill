package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopEmployee;
import ch.Elodin.RealmQuill.repository.shop.ShopEmployeeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ShopEmployee")
public class ShopEmployeeController extends GenericController<ShopEmployee, Integer> {
    public ShopEmployeeController(ShopEmployeeRepository repository) {
        super(repository);
    }
}




