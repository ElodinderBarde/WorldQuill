package ch.Elodin.RealmQuill.dto.notes;

public record FolderReadDTO(
        Long id,
        String name,
        Long parentId,
        Long categoryId
) {
}
