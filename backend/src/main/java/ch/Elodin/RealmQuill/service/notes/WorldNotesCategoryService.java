package ch.Elodin.RealmQuill.service.notes;


import ch.Elodin.RealmQuill.dto.notes.CategoryReadDTO;
import ch.Elodin.RealmQuill.dto.notes.CategoryWriteDTO;
import ch.Elodin.RealmQuill.mapper.notes.WorldNotesCategoryMapper;
import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldNotesCategoryService {

    private final WorldNotesCategoryRepository repo;
    private final AppUserRepository userRepository;
    private final WorldNotesCategoryMapper mapper;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        if ("anonymousUser".equals(username)) {
            username = "Elodin"; // oder der Username aus DataInitializer
        }

        String finalUsername = username;
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + finalUsername));
    }

    // CREATE
    public CategoryReadDTO create(CategoryWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = mapper.toEntity(dto);
        category.setUser(user);

        repo.save(category);
        return mapper.toReadDTO(category);
    }

    // UPDATE
    public CategoryReadDTO update(Long id, CategoryWriteDTO dto) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = repo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        mapper.updateFromDTO(dto, category);

        repo.save(category);
        return mapper.toReadDTO(category);
    }

    // DELETE
    public void delete(Long id) {
        AppUser user = getCurrentUser();

        WorldNotesCategory category = repo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        repo.delete(category);
    }

    // GET ALL
    public List<CategoryReadDTO> getAll() {
        AppUser user = getCurrentUser();
        return mapper.toReadDTOs(repo.findAllByUser(user));
    }
}
