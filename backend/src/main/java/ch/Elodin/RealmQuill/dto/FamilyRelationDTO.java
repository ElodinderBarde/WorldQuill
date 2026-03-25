package ch.Elodin.RealmQuill.dto;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.Family.FamilyRelation;

public record FamilyRelationDTO(
        int relatedNpcId,
        String relatedNpcName,
        String relationType,
        String pictureUrl
) {
    public static FamilyRelationDTO from(FamilyRelation r) {
        Npc rel = r.getRelatedNpc();
        String firstname = rel.getFirstname() != null
                ? rel.getFirstname().getFirstname() : "?";
        String lastname = rel.getLastname() != null
                ? rel.getLastname().getLastname() : "?";
        return new FamilyRelationDTO(
                rel.getNpc_ID(),
                firstname + " " + lastname,
                r.getRelationType().name(),
                rel.getPicture() != null ? rel.getPicture().getPicture() : null
        );
    }
}
