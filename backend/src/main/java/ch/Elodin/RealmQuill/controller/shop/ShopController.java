package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.ShopDTO;
import ch.Elodin.RealmQuill.mapper.NpcMapper;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.shop.ShopType;
import ch.Elodin.RealmQuill.model.world.Location;
import ch.Elodin.RealmQuill.repository.npcinfo.StatsRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.shop.ShopService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {

    private final ShopService shopService;
    private final ShopTypeRepository shopTypeRepository;
    private final LocationRepository locationRepository;
    private final NpcRepository npcRepository;
    private final StatsRepository statsRepository;
    public ShopController(
            ShopService shopService,
            ShopTypeRepository shopTypeRepository,
            LocationRepository locationRepository,
            StatsRepository statsRepository,
            NpcRepository npcRepository) { // hinzufÃ¼gen

        this.shopService = shopService;
        this.shopTypeRepository = shopTypeRepository;
        this.locationRepository = locationRepository;
        this.npcRepository = npcRepository; // speichern
        this.statsRepository = statsRepository;
    }


    @GetMapping
    public List<ShopDTO> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable Integer id) {
        return shopService.getShopById(id)
                .map(shopService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO dto) {
        try {
            ShopType shopType = shopTypeRepository.findById(dto.getShopTypeId())
                    .orElseThrow(() -> new RuntimeException("ShopType nicht gefunden: ID " + dto.getShopTypeId()));

            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location nicht gefunden: ID " + dto.getLocationId()));

            Shop shop = shopService.fromDTO(dto, shopType, location);
            Shop saved = shopService.createShop(shop);
            return ResponseEntity.ok(shopService.toDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShopNote(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Optional<Shop> optionalShop = shopService.getShopEntityById(id); // Sauberer Zugriff Ã¼ber Service
        if (optionalShop.isEmpty()) return ResponseEntity.notFound().build();

        Shop shop = optionalShop.get();
        shop.setNotes(body.get("notes"));

        Shop saved = shopService.saveShop(shop); // auch Ã¼ber Service

        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<Object> deleteShop(@PathVariable Integer id) {
        return shopService.getShopById(id).map(shop -> {
            shopService.deleteShop(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{shopId}/employees")
    public List<NpcReadDTO> getEmployeesByShop(@PathVariable int shopId) {
        return npcRepository
                .findEmployeesByShopId(shopId)
                .stream()
                .map(npc -> {
                    Stats stats = statsRepository.findByNpc(npc).orElse(null);
                    return NpcMapper.toReadDTO(npc, stats);
                })
                .collect(Collectors.toList());
    }


    @GetMapping("/{shopId}/customers")
    public List<NpcReadDTO> getCustomersByShop(@PathVariable int shopId) {
        return npcRepository
                .findCustomersByShopId(shopId)
                .stream()
                .map(npc -> {
                    Stats stats = statsRepository.findByNpc(npc).orElse(null);
                    return NpcMapper.toReadDTO(npc, stats);
                })
                .collect(Collectors.toList());
    }



}



