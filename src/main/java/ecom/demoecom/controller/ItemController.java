package ecom.demoecom.controller;

import ecom.demoecom.entity.*;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @GetMapping("/manage-item")
    public String manageItem(HttpSession session,Model model) {
        return "/admin/manageItems";
    }
    // Lấy danh sách tất cả các Item
    @GetMapping("/items-list")
    public String listItems(HttpSession session,Model model) {
        String username = (String) session.getAttribute("username");
        Long accountId = (Long) session.getAttribute("accountId");
        Cart cart = cartService.getCartByAccountId(accountId);


        // Add the cartId to the model
        if (cart != null) {
            model.addAttribute("cartId", cart.getId());
            model.addAttribute("cart", cart );
        }
        model.addAttribute("username", username);
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "items/listItems"; // Tên của template là listItem.html
    }

    // Lấy danh sách các sản phẩm Book
    @GetMapping("/items/book")
    public String listBooks(Model model) {
        List<Book> books = itemService.getBooks();
        model.addAttribute("items", books);
        return "items/listItems"; // Tên của template là listItem.html, tái sử dụng để hiển thị Books
    }

    // Lấy danh sách Laptop
    @GetMapping("/items/laptop")
    public String listLaptops(Model model) {
        List<Laptop> laptops = itemService.getLaptops();
        model.addAttribute("items", laptops);
        return "items/listItems"; // Tái sử dụng template listItems.html
    }

    // Lấy danh sách Clothes
    @GetMapping("/items/clothes")
    public String listClothes(Model model) {
        List<Clothes> clothes = itemService.getClothes();
        model.addAttribute("items", clothes);
        return "items/listItems"; // Tái sử dụng template listItems.html
    }

    // Lấy danh sách Shoes
    @GetMapping("/items/shoes")
    public String listShoes(Model model) {
        List<Shoes> shoes = itemService.getShoes();
        model.addAttribute("items", shoes);
        return "items/listItems"; // Tái sử dụng template listItems.html
    }

    @PostMapping("items/add")
    public String addItem(
            @RequestParam("category") String category,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam Map<String, String> additionalAttributes,
            Model model) throws IOException {

        switch (category.toLowerCase()) {
            case "laptop":
                itemService.saveLaptop(name, price, quantity, description, image, additionalAttributes.get("laptopProducer"), additionalAttributes.get("laptopType"));
                break;
            case "book":
                itemService.saveBook(name, price, quantity, description, image, additionalAttributes.get("bookType"), additionalAttributes.get("author"), additionalAttributes.get("publisher"));
                break;
            case "shoes":
                itemService.saveShoes(name, price, quantity, description, image, additionalAttributes.get("shoesType"), additionalAttributes.get("shoesProducer"), additionalAttributes.get("shoesSize"));
                break;
            case "clothes":
                itemService.saveClothes(name, price, quantity, description, image, additionalAttributes.get("clothesType"), additionalAttributes.get("clothesProducer"), additionalAttributes.get("clothesSize"));
                break;
            default:
                model.addAttribute("error", "Invalid category provided");
                return "error";
        }

        // Return success view
        return "itemSuccess";
    }
}
