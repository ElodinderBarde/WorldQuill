package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/symbol")
public class SymbolController {
    @GetMapping
    public EnumSymbol[] getAllSymbols() {
        return EnumSymbol.values();
    }
}
