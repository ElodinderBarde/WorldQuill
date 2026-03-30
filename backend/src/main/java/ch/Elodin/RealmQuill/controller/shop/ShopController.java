package ch.Elodin.RealmQuill.controller.shop;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.ShopDTO;
import ch.Elodin.RealmQuill.mapper.NpcMapper;
import ch.Elodin.RealmQuill.mapper.ShopMapper;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.shop.ShopType;
import ch.Elodin.RealmQuill.model.world.Location;
import ch.Elodin.RealmQuill.repository.npcinfo.StatsRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import ch.Elodin.RealmQuill.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopRepository shopRepository;
    private final LocationRepository locationRepository;
    private final NpcRepository npcRepository;
    private final StatsRepository statsRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final ShopService shopService;



    @GetMapping
    public List<ShopDTO> getAll() {
        return ShopMapper.toDTOList(shopRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getById(@PathVariable int id) {
        return shopRepository.findById(id)
                .map(ShopMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byCampaign/{campaignId}")
    public List<ShopDTO> getByCampaign(@PathVariable int campaignId) {
        return ShopMapper.toDTOList(shopRepository.findByCampaignId(campaignId));
    }

    @GetMapping("/byLocation/{locationId}")
    public List<ShopDTO> getByLocation(@PathVariable int locationId) {
        return ShopMapper.toDTOList(shopRepository.findByLocationId(locationId));
    }




    @DeleteMapping("/{id}")
    public HttpEntity<Object> deleteShop(@PathVariable Integer id) {
        return shopService.getShopById(id).map(shop -> {
            shopService.deleteShop(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
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
}
