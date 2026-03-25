package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.service.NpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/npcs/write")
@RequiredArgsConstructor
public class NpcWriteController {
    private final NpcService npcService;

    @PostMapping
    public ResponseEntity<?> createNpc(@RequestBody NpcWriteDTO dto) {
        npcService.createNpc(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNpc(@PathVariable int id, @RequestBody NpcWriteDTO dto) {
        npcService.updateNpc(id, dto);
        return ResponseEntity.ok().build();
    }
}
