package rostyk.stupnytskiy.andromeda.dto.response.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Property;

@Getter
@Setter
public class PropertyResponse {
    private Long id;
    private String name;
    private String value;

    public PropertyResponse(Property property){
        this.id = property.getId();
        this.name = property.getName();
        this.value = property.getValue();
    }

}
