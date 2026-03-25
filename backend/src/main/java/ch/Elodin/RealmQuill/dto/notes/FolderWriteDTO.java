package ch.Elodin.RealmQuill.dto.notes;

public record FolderWriteDTO(
        String name,
        Long parentId,
        Long categoryId
) {
}
