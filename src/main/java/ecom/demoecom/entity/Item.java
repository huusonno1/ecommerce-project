package ecom.demoecom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private double price;
    private int quantity;
    private String description;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public boolean isBook() {
        return this instanceof Book;
    }

    public boolean isLaptop() {
        return this instanceof Laptop;
    }

    public boolean isClothes() {
        return this instanceof Clothes;
    }

    public boolean isShoes() {
        return this instanceof Shoes;
    }

    // Getters and setters
}