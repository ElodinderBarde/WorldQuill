package ch.Elodin.RealmQuill.dto.notes;

public record LinkReadDTO(
        Long id,
        Long noteId,
        Long targetNoteId,
        String linkUrl,
        String linkText
) {}
