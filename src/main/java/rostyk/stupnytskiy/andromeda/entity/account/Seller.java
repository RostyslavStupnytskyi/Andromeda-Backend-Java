package rostyk.stupnytskiy.andromeda.entity.account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.statistics.SellerStatistics;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxpayerNumber;

    private String shopName;

    private Boolean onlySellerCountry;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seller")
    private Account account;

    @OneToMany(mappedBy = "seller")
    private List<Advertisement> advertisements;

    @OneToOne(cascade =  CascadeType.ALL)
    private SellerStatistics statistics;

    @ManyToMany
    private Set<Country> countries;

    @ManyToMany
    private Set<DeliveryType> deliveryTypes;
}
