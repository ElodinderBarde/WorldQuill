package ch.Elodin.RealmQuill.controller;

import ch.Elodin.RealmQuill.dto.ItemDTO;
import ch.Elodin.RealmQuill.service.shop.ItemService;
import java.util.List;

import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/resources")
    public List<String> getAllResources() {
        return itemService.getAllResources();
    }

    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public ItemDTO createItem(@RequestBody ItemDTO dto) {
        return itemService.createItem(dto);
    }
    @GetMapping("/rarities")
    public  List<String> GetAllRarities () {
        return itemService.GetAllRarities();}


    @GetMapping("/types")
    public  List<String> GetAllTypes () {
        return itemService.GetAllTypes();}
    }



