package ch.Elodin.RealmQuill.service.Login;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {

    private final AppUserRepository userRepository;

    public UserContextService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
