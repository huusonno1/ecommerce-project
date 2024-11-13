package ecom.demoecom.service;

import ecom.demoecom.entity.Item;
import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);

    void saveItem(Item item);
}
