package ch.Elodin.RealmQuill.controller;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class GenericController<T, ID> {

    protected final JpaRepository<T, ID> repository;
    private final Random random = new Random();

    protected GenericController(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<T> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        Optional<T> optional = repository.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/random")
    public ResponseEntity<T> getRandom() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(all.get(random.nextInt(all.size())));
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return repository.save(entity);
    }
}



