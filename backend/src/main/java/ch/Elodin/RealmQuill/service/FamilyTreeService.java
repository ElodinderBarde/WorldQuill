// ch/Elodin/RealmQuill/service/FamilyTreeService.java
package ch.Elodin.RealmQuill.service;

import ch.Elodin.RealmQuill.model.Family.FamilyRelation;
import ch.Elodin.RealmQuill.model.Family.RelationType;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.repository.npcinfo.Family.FamilyRelationRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FamilyTreeService {

    private final NpcGeneratorService generatorService;
    private final NpcRepository npcRepository;
    private final FamilyRelationRepository relationRepository;

    private static final List<RelationType> WEIGHTED_ROLES = List.of(
            RelationType.PARTNER,
            RelationType.PARENT,      RelationType.PARENT,
            RelationType.CHILD,       RelationType.CHILD,
            RelationType.SIBLING,
            RelationType.GRANDPARENT,
            RelationType.PARENT_IN_LAW,
            RelationType.SIBLING_IN_LAW,
            RelationType.GRANDCHILD
    );

    private static final Set<RelationType> SINGULAR_ROLES = Set.of(
            RelationType.PARTNER
    );

    public FamilyTreeService(NpcGeneratorService generatorService,
                             NpcRepository npcRepository,
                             FamilyRelationRepository relationRepository) {
        this.generatorService = generatorService;
        this.npcRepository = npcRepository;
        this.relationRepository = relationRepository;
    }

    @Transactional
    public List<FamilyRelation> generateFamilyFor(int sourceNpcId, int memberCount) {
        Npc sourceNpc = npcRepository.findById(sourceNpcId)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + sourceNpcId));

        Set<RelationType> usedSingularRoles = new HashSet<>();
        List<FamilyRelation> result = new ArrayList<>();
        Random random = new Random();

        int attempts = 0;
        while (result.size() < memberCount && attempts < memberCount * 5) {
            attempts++;

            RelationType role = WEIGHTED_ROLES.get(random.nextInt(WEIGHTED_ROLES.size()));

            if (SINGULAR_ROLES.contains(role) && usedSingularRoles.contains(role)) {
                continue;
            }

            Npc relative = generatorService.generateRandomNpc();
            relative.setRace(sourceNpc.getRace());
            relative.setLastname(sourceNpc.getLastname());
            relative = npcRepository.save(relative);

            saveRelation(sourceNpc, relative, role);
            saveRelation(relative, sourceNpc, role.inverse());

            if (SINGULAR_ROLES.contains(role)) {
                usedSingularRoles.add(role);
            }

            FamilyRelation ref = new FamilyRelation();
            ref.setSourceNpc(sourceNpc);
            ref.setRelatedNpc(relative);
            ref.setRelationType(role);
            result.add(ref);
        }

        return result;
    }

    private void saveRelation(Npc source, Npc related, RelationType type) {
        if (relationRepository.existsBySourceNpcAndRelatedNpcAndRelationType(source, related, type)) {
            return;
        }
        FamilyRelation rel = new FamilyRelation();
        rel.setSourceNpc(source);
        rel.setRelatedNpc(related);
        rel.setRelationType(type);
        relationRepository.save(rel);
    }
}