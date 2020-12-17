package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.*;
import rostyk.stupnytskiy.andromeda.entity.account.Seller;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;

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

    private Boolean onlySellerCountry;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Subcategory subcategory;

    @OneToMany(mappedBy = "advertisement")
    private List<Property> properties;

    @ManyToOne
    private Seller seller;

    @OneToMany(mappedBy = "advertisement")
    private List<CartItem> cartItems;

    @OneToOne(cascade =  CascadeType.ALL)
    private AdvertisementStatistics statistics;

    @OneToMany(mappedBy = "advertisement")
    private List<RetailPrice> retailPrices;

    @OneToMany(mappedBy = "advertisement")
    private List<WholesalePrice> wholesalePrices;
}
