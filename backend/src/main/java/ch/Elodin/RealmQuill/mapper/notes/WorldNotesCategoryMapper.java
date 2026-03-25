package ch.Elodin.RealmQuill.mapper.notes;

import ch.Elodin.RealmQuill.dto.notes.CategoryReadDTO;
import ch.Elodin.RealmQuill.dto.notes.CategoryWriteDTO;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorldNotesCategoryMapper {

    CategoryReadDTO toReadDTO(WorldNotesCategory entity);
    List<CategoryReadDTO> toReadDTOs(List<WorldNotesCategory> entities);

    // DTO → ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "folders", ignore = true)
    WorldNotesCategory toEntity(CategoryWriteDTO dto);

    // UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "folders", ignore = true)
    void updateFromDTO(CategoryWriteDTO dto, @MappingTarget WorldNotesCategory entity);
}
