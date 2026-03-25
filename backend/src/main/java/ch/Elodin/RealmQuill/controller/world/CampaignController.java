package ch.Elodin.RealmQuill.controller.world;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.repository.world.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignRepository campaignRepository;

    @GetMapping
    public List<Campaign> getAll() { return campaignRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getById(@PathVariable int id) {
        return campaignRepository.findById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Campaign> create(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignRepository.save(campaign));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> update(@PathVariable int id, @RequestBody Campaign dto) {
        Campaign c = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kampagne nicht gefunden: " + id));
        c.setCampaignName(dto.getCampaignName());
        return ResponseEntity.ok(campaignRepository.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        campaignRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
