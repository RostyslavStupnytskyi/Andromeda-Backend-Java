package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertiesRequest {
    private List<PropertyRequest> properties;
    private Long advertisementId;
}
