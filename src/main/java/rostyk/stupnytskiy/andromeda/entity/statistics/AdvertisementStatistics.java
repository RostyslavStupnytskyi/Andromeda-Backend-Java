package rostyk.stupnytskiy.andromeda.entity.statistics;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePrice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class AdvertisementStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long allViews;

    private Long userViews;

    private Boolean confirmed;

    private LocalDateTime creationDate;

    private Double rating;

    @OneToOne(mappedBy = "statistics")
    private Advertisement advertisement;

    @OneToMany(mappedBy = "statistics")
    private Set<RetailPrice> retailPrices;

    @OneToMany(mappedBy = "statistics")
    private Set<WholesalePrice> wholesalePrices;
}
