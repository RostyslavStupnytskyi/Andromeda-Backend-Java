package rostyk.stupnytskiy.andromeda.dto.response.statistics.diagrams;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnDiagramDataResponse {

    private Integer max;
    private Integer min;
    private Integer average;
    private Double averageValue;
    private List<DiagramColumnResponse> columns = new ArrayList<>();

    public ColumnDiagramDataResponse(Integer max, Integer min, Double averageValue, List<DiagramColumnResponse> columns) {
        this.max = max;
        this.min = min;
        this.average = (max - min) / 2;
        if (averageValue == null && columns.size() != 0)
            this.averageValue = columns.stream().mapToInt(DiagramColumnResponse::getValue).average().getAsDouble();
        else
            this.averageValue = averageValue;
        this.columns = columns;
    }
}
