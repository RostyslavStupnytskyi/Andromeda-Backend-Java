package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsAdvertisement advertisement;

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                '}';
    }
}
