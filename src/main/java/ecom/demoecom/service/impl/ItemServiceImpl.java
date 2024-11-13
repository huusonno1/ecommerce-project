package ecom.demoecom.service.impl;

import ecom.demoecom.entity.*;
import ecom.demoecom.repo.*;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClothesRepository clothesRepository;
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private ShoesRepository shoesRepository;

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(getBooks());
        allItems.addAll(getLaptops());
        allItems.addAll(getClothes());
        allItems.addAll(getShoes());
        return allItems;
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Clothes> getClothes() {
        return clothesRepository.findAll();
    }

    @Override
    public List<Laptop> getLaptops() {
        return laptopRepository.findAll();
    }

    @Override
    public List<Shoes> getShoes() {
        return shoesRepository.findAll();
    }
}
