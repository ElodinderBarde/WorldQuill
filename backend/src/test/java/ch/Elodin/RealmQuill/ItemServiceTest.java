package ch.Elodin.RealmQuill;

import ch.Elodin.RealmQuill.dto.ItemDTO;
import model.Item;
import ch.Elodin.RealmQuill.repository.ItemRepository;

import ch.Elodin.RealmQuill.service.shop.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    @Test
    public void testGetAllResources_returnsDistinctBuchValues() {
        List<String> mockValues = List.of("Buch A", "Buch B");
        when(itemRepository.findDistinctBuchValues()).thenReturn(mockValues);

        List<String> result = itemService.getAllResources();

        assertEquals(2, result.size());
        assertTrue(result.contains("Buch A"));
        verify(itemRepository, times(1)).findDistinctBuchValues();
    }

    @Test
    public void testCreateItem_savesItemAndReturnsDTO() {
        ItemDTO dto = new ItemDTO(
                0, "Test Item", 100, "Gift", "Selten",
                "Buch X", 10, 11, 12, null, "Ein Test-Gegenstand"
        );

        Item savedItem = new Item();
        savedItem.setItemID(1);
        savedItem.setItemName("Test Item");
        savedItem.setPrice(100);
        savedItem.setTyp("Gift");
        savedItem.setSeltenheit("Selten");
        savedItem.setBuch("Buch X");
        savedItem.setSeite1(10);
        savedItem.setSeite2(11);
        savedItem.setSeite3(12);
        savedItem.setEinstimmung(null);
        savedItem.setBeschreibung("Ein Test-Gegenstand");

        when(itemRepository.save(Mockito.any(Item.class))).thenReturn(savedItem);

        ItemDTO result = itemService.createItem(dto);

        assertEquals("Test Item", result.getName());
        assertEquals("Buch X", result.getBuch());
        assertEquals(1, result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }
}

