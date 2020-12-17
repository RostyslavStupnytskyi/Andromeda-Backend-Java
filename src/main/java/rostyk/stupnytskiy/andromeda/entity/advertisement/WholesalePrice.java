package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;

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

    @ManyToOne
    private Advertisement advertisement;
}
