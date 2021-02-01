package rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserAdvertisementsViewsDateSectionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

    private List<UserAdvertisementViewResponse> advertisements;

    public UserAdvertisementsViewsDateSectionResponse(LocalDate date, List<UserAdvertisementViewResponse> advertisements) {
        this.date = date;
        this.advertisements = advertisements;
    }
}
