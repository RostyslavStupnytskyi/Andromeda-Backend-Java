package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class RetailPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private Double price;

    @OneToOne(mappedBy = "retailPrice")
    private Advertisement advertisement;

    @ManyToOne
    private AdvertisementStatistics statistics;
}
