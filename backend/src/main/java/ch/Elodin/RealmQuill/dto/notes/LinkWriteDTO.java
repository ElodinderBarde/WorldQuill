package ch.Elodin.RealmQuill.dto.notes;

public record LinkWriteDTO(
        String url,
        String text,
        Long targetNoteId
) {}
