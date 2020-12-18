package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class WholesalePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "wholesalePrice")
    private List<WholesalePriceUnit> priceUnits;

    @OneToOne(mappedBy = "wholesalePrice")
    private Advertisement advertisement;

    @ManyToOne
    private AdvertisementStatistics statistics;
}
