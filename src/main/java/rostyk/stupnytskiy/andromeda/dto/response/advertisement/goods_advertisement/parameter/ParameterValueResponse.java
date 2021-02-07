package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValue;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class ParameterValueResponse {

    private Long id;
    private String title;
    private String image;

    public ParameterValueResponse(ParameterValue parameterValue) {
        this.id = parameterValue.getId();
        this.title = parameterValue.getTitle();
        this.image = parameterValue.getImage();
    }
}
