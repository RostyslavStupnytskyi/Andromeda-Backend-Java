package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.*;
import rostyk.stupnytskiy.andromeda.entity.account.Seller;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
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

    private Boolean isRetail = false;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Subcategory subcategory;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();

    @ManyToOne
    private Seller seller;

    @OneToMany(mappedBy = "advertisement")
    private List<CartItem> cartItems;

    @OneToOne(cascade = CascadeType.ALL)
    private AdvertisementStatistics statistics;

    @OneToMany(mappedBy = "advertisement")
    private List<RetailPrice> retailPrices = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<WholesalePrice> wholesalePrices = new ArrayList<>();

    @ManyToOne
    private Currency currency;

    public RetailPrice getCurrentRetailPrice() {
        this.retailPrices.sort(Comparator.comparing(RetailPrice::getDateTime));
        return this.retailPrices.get(this.retailPrices.size() - 1);
    }

    public WholesalePrice getCurrentWholeSalePrice() {
        this.wholesalePrices.sort(Comparator.comparing(WholesalePrice::getDateTime));
        return this.wholesalePrices.get(this.wholesalePrices.size() - 1);
    }

}
