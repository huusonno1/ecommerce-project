package ecom.demoecom.controller;

import ecom.demoecom.entity.Item;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public String viewItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "items"; // HTML page to display all items
    }

    @GetMapping("/items/{itemId}")
    public String viewItemDetails(@PathVariable Long itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        model.addAttribute("item", item);
        return "item-details"; // HTML page to display item details
    }

    @GetMapping("/items/add")
    public String showAddItemForm() {
        return "add-item"; // Returns the add-item.html template
    }

    @PostMapping("/items/add")
    public String addItem(@RequestParam String name, @RequestParam String image,
                          @RequestParam double price, @RequestParam int quantity,
                          @RequestParam String description) {
        Item item = new Item();
        item.setName(name);
        item.setImage(image);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setDescription(description);
        itemService.saveItem(item);
        return "redirect:/items"; // Redirect to the items page
    }
}
