package ch.Elodin.RealmQuill.dto.notes;

import java.time.Instant;
import java.util.List;

public record NoteReadDTO(
        Long id,
        String title,
        String content,
        Long folderId,
        Long categoryId,
        List<Long> linkIds,
        Instant createdAt,
        Instant updatedAt
) {}
