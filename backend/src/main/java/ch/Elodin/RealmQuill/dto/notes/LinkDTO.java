package ch.Elodin.RealmQuill.dto.notes;


public record LinkDTO(
        Long id,
        String url,
        String description,
        Long targetNoteId,
        Integer version,
        String createdAt,
        String updatedAt
) {
}
