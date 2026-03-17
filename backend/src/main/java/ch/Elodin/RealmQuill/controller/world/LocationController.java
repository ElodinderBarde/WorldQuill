package ch.Elodin.RealmQuill.controller.world;

import ch.Elodin.RealmQuill.repository.world.LocationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;


    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(loc -> new LocationDTO(
                        loc.getId(),
                        loc.getCityID() != null ? loc.getCityID().getCity_name() : null,
                        loc.getVillageID() != null ? loc.getVillageID().getName() : null
                ))
                .toList();
    }

    public record LocationDTO(int locationId, String cityName, String villageName) {
    }


    @GetMapping("/cities")
    public List<CityDTO> getCities() {
        return locationRepository.findAll().stream()
                .filter(loc -> loc.getCityID() != null)
                .map(loc -> new CityDTO(loc.getId(), loc.getCityID().getCity_name()))
                .toList();
    }

    // Inneres DTO oder eigene Datei
    public record CityDTO(int id, String city_name) {
    }


}


