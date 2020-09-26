package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String mainImage;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    private Subcategory subcategory;

    private Double rating;

    private Integer price;

    private Long allViews;

    private Long userViews;

    @OneToMany(mappedBy = "advertisement")
    private List<Property> properties;

    @ManyToOne
    private Seller seller;

    @OneToMany(mappedBy = "advertisement")
    private List<CartItem> cartItems;
}
