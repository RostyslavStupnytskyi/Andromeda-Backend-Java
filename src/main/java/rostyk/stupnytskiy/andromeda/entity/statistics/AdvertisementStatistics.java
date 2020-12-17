package rostyk.stupnytskiy.andromeda.entity.statistics;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
