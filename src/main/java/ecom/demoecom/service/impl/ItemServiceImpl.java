package ecom.demoecom.service.impl;

import ecom.demoecom.entity.*;
import ecom.demoecom.repo.*;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    // Directory to save uploaded images
    private final String uploadDir = "uploads/";

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

    @Override
    public void saveLaptop(String name, double price, int quantity, String description,
                           MultipartFile image, String laptopProducer, String laptopType
    ) throws IOException {
        Laptop laptop = new Laptop();
        laptop.setName(name);
        laptop.setPrice(price);
        laptop.setQuantity(quantity);
        laptop.setDescription(description);
        laptop.setLaptopProducer(laptopProducer);
        laptop.setLaptopType(laptopType);

        if (image != null && !image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            laptop.setImage(imageName);
        }

        laptopRepository.save(laptop);
    }

    @Override
    public void saveBook(String name, double price, int quantity, String description,
                         MultipartFile image, String bookType, String author, String publisher
    ) throws IOException {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setDescription(description);
        book.setBookType(bookType);
        book.setAuthor(author);
        book.setPublisher(publisher);

        if (image != null && !image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            book.setImage(imageName);
        }

        bookRepository.save(book);
    }

    @Override
    public void saveShoes(String name, double price, int quantity, String description,
                          MultipartFile image, String shoesType, String shoesProducer, String shoesSize
    ) throws IOException {
        Shoes shoes = new Shoes();
        shoes.setName(name);
        shoes.setPrice(price);
        shoes.setQuantity(quantity);
        shoes.setDescription(description);
        shoes.setShoesType(shoesType);
        shoes.setShoesProducer(shoesProducer);
        shoes.setShoesSize(shoesSize);

        if (image != null && !image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            shoes.setImage(imageName);
        }

        shoesRepository.save(shoes);
    }

    @Override
    public void saveClothes(String name, double price, int quantity, String description,
                            MultipartFile image, String clothesType, String clothesProducer, String clothesSize
    ) throws IOException {
        Clothes clothes = new Clothes();
        clothes.setName(name);
        clothes.setPrice(price);
        clothes.setQuantity(quantity);
        clothes.setDescription(description);
        clothes.setClothesType(clothesType);
        clothes.setClothesProducer(clothesProducer);
        clothes.setClothesSize(clothesSize);

        if (image != null && !image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            clothes.setImage(imageName);
        }

        clothesRepository.save(clothes);
    }

}
