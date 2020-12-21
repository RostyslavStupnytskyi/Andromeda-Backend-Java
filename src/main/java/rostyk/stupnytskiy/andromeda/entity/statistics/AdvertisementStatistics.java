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

    private Long allViews = 0L;

    private Long userViews = 0L;

    private Boolean confirmed = false;

    private LocalDateTime creationDate;

    private Double rating;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "statistics")
    private Advertisement advertisement;
}
