package ecom.demoecom.service;

import ecom.demoecom.entity.*;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);

    void saveItem(Item item);

    List<Book> getBooks();
    List<Clothes> getClothes();
    List<Laptop> getLaptops();
    List<Shoes> getShoes();
}
