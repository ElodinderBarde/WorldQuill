package ch.Elodin.RealmQuill.controller.world;
import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5137")
@RestController @RequestMapping("/api/locations") @RequiredArgsConstructor
public class LocationController {
    private final LocationRepository locationRepository;

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(loc -> new LocationDTO(
                        loc.getId(),
                        loc.getCity() != null ? loc.getCity().getCityName() : null,
                        loc.getVillage() != null ? loc.getVillage().getName() : null,
                        loc.getLocationName(),
                        loc.getLocationMap()
                ))
                .toList();
    }

    @GetMapping("/cities")
    public List<CityDTO> getCities() {
        return locationRepository.findAll().stream()
                .filter(loc -> loc.getCity() != null)
                .map(loc -> new CityDTO(loc.getId(), loc.getCity().getCityName()))
                .toList();
    }

    @GetMapping("/byCampaign/{campaignId}")
    public List<LocationDTO> getByCampaign(@PathVariable("campaignId") int campaignId) {
        return locationRepository.findByCampaignId(campaignId).stream()
                .map(loc -> new LocationDTO(
                        loc.getId(),
                        loc.getCity() != null ? loc.getCity().getCityName() : null,
                        loc.getVillage() != null ? loc.getVillage().getName() : null,
                        loc.getLocationName(),
                        loc.getLocationMap()
                ))
                .toList();
    }

    public record LocationDTO(int locationId, String cityName, String villageName, String questlocation, String locationMap) {}

    public record CityDTO(int id, String cityName) {}
}