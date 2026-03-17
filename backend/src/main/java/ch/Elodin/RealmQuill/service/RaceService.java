package ch.Elodin.RealmQuill.service;

import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.repository.npcinfo.RaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RaceService {

    private final RaceRepository raceRepository;

    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }
}



