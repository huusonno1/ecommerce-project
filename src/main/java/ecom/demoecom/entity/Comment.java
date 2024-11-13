package ecom.demoecom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
}
