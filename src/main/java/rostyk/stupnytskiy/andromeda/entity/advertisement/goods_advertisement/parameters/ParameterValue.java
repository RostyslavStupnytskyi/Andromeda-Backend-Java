package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ParameterValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String image;

    @ManyToOne
    private Parameter parameter;

    @ManyToMany(mappedBy = "values")
    private List<ParametersValuesPriceCount> valuesPriceCounts;
}
