package ecom.demoecom.service;

import ecom.demoecom.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);

    void saveItem(Item item);

    List<Book> getBooks();
    List<Clothes> getClothes();
    List<Laptop> getLaptops();
    List<Shoes> getShoes();

    void saveLaptop(String name, double price, int quantity, String description, MultipartFile image, String laptopProducer, String laptopType) throws IOException;

    void saveBook(String name, double price, int quantity, String description, MultipartFile image, String bookType, String author, String publisher) throws IOException;

    void saveShoes(String name, double price, int quantity, String description, MultipartFile image, String shoesType, String shoesProducer, String shoesSize) throws IOException;

    void saveClothes(String name, double price, int quantity, String description, MultipartFile image, String clothesType, String clothesProducer, String clothesSize) throws IOException;
}
