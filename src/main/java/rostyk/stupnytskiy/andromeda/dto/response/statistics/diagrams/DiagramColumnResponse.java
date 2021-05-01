package rostyk.stupnytskiy.andromeda.dto.response.statistics.diagrams;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiagramColumnResponse {

    private Integer value = 0;
    private Integer percent = 0;
    private Boolean showable = false;
}
