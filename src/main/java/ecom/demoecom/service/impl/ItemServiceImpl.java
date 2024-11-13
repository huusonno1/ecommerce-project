package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Item;
import ecom.demoecom.repo.ItemRepository;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
}
