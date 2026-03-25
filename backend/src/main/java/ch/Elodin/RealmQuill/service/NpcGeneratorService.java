package ch.Elodin.RealmQuill.service;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.repository.npcinfo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
@Service @RequiredArgsConstructor
public class NpcGeneratorService {
    private final FirstnameRepository firstnameRepo;
    private final LastnameRepository lastnameRepo;
    private final RaceRepository raceRepo;
    private final ArmorRepository armorRepo;
    private final BackgroundRepository backgroundRepo;
    private final BeardstyleRepository beardstyleRepo;
    private final BetonungRepository betonungRepo;
    private final DislikesRepository dislikesRepo;
    private final FlawRepository flawRepo;
    private final GenderRepository genderRepo;
    private final HaircolorRepository haircolorRepo;
    private final HairstyleRepository hairstyleRepo;
    private final IdealsRepository idealsRepo;
    private final JacketsRepository jacketsRepo;
    private final JewelleryRepository jewelleryRepo;
    private final NpcClassRepository npcClassRepo;
    private final KleidungQualiRepository kleidungQualiRepo;
    private final LevelRepository levelRepo;
    private final LikesRepository likesRepo;
    private final OtherDescriptionRepository otherDescriptionRepo;
    private final PersonalityRepository personalityRepo;
    private final TalkingStyleRepository talkingStyleRepo;
    private final TrousersRepository trousersRepo;
    public Npc generateRandomNpc() {
        Npc npc = new Npc();
        npc.setFirstname(getRandom(firstnameRepo.findAll()));
        npc.setLastname(getRandom(lastnameRepo.findAll()));
        npc.setRace(getRandom(raceRepo.findAll()));
        npc.setArmor_ID(getRandom(armorRepo.findAll()));
        npc.setBackground(getRandom(backgroundRepo.findAll()));
        npc.setBeardstyle(getRandom(beardstyleRepo.findAll()));
        npc.setBetonung(getRandom(betonungRepo.findAll()));
        npc.setDislikes(getRandom(dislikesRepo.findAll()));
        npc.setFlaw(getRandom(flawRepo.findAll()));
        npc.setGender(getRandom(genderRepo.findAll()));
        npc.setHaircolor(getRandom(haircolorRepo.findAll()));
        npc.setHairstyle(getRandom(hairstyleRepo.findAll()));
        npc.setIdeals(getRandom(idealsRepo.findAll()));
        npc.setJackets(getRandom(jacketsRepo.findAll()));
        npc.setJewellery(getRandom(jewelleryRepo.findAll()));
        npc.setNpcClass(getRandom(npcClassRepo.findAll()));
        npc.setKleidungQuali(getRandom(kleidungQualiRepo.findAll()));
        npc.setLevel(getRandom(levelRepo.findAll()));
        npc.setLikes(getRandom(likesRepo.findAll()));
        npc.setOtherDescription(getRandom(otherDescriptionRepo.findAll()));
        npc.setPersonality(getRandom(personalityRepo.findAll()));
        npc.setTalkingstyle(getRandom(talkingStyleRepo.findAll()));
        npc.setTrousers(getRandom(trousersRepo.findAll()));
        return npc;
    }
    private <T> T getRandom(List<T> list) {
        if (list.isEmpty()) throw new RuntimeException("Leere Liste beim NPC generieren");
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}