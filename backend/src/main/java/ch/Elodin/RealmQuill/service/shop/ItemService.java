package ch.Elodin.RealmQuill.service.shop;
import ch.Elodin.RealmQuill.dto.ItemDTO;
import ch.Elodin.RealmQuill.model.Item;
import ch.Elodin.RealmQuill.repository.world.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public List<String> getAllResources() { return itemRepository.findDistinctSourceBooks(); }
    public List<ItemDTO> getAllItems() { return itemRepository.findAll().stream().map(this::toDTO).toList(); }
    public ItemDTO createItem(ItemDTO dto) {
        Item item = new Item();
        item.setItemName(dto.getName()); item.setPrice(dto.getPrice());
        item.setItemType(dto.getItemType()); item.setRarity(dto.getRarity());
        item.setSourceBook(dto.getSourceBook()); item.setPage1(dto.getPage1());
        item.setPage2(dto.getPage2()); item.setPage3(dto.getPage3());
        item.setAttunement(dto.getAttunement()); item.setDescription(dto.getDescription());
        return toDTO(itemRepository.save(item));
    }
    public List<String> GetAllRarities() { return itemRepository.findAll().stream().map(Item::getRarity).distinct().toList(); }
    public List<String> GetAllTypes() { return itemRepository.findAll().stream().map(Item::getItemType).distinct().toList(); }
    private ItemDTO toDTO(Item item) {
        return new ItemDTO(item.getId(), item.getItemName(), item.getPrice(),
            item.getItemType(), item.getRarity(), item.getSourceBook(),
            item.getPage1(), item.getPage2(), item.getPage3(), item.getAttunement(), item.getDescription());
    }
}