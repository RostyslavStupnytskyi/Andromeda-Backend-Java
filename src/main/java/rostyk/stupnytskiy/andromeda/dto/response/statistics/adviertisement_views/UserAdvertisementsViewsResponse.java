package rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class UserAdvertisementsViewsResponse {
    private Integer totalPages;
    private Long totalElements;

    private List<UserAdvertisementsViewsDateSectionResponse> sections = new ArrayList<>();

    public UserAdvertisementsViewsResponse(PageResponse<UserAdvertisementViewResponse> viewResponse) {
        this.totalPages = viewResponse.getTotalPages();
        this.totalElements = viewResponse.getTotalElements();
        makeSections(viewResponse.getData());
    }

    private void makeSections(List<UserAdvertisementViewResponse> responses) {
        List<LocalDate> dates = getDates(responses);

        for (LocalDate date : dates) {
            List<UserAdvertisementViewResponse> section = new ArrayList<>();
            responses.forEach((r) -> {
                if (r.getDate().equals(date))
                    section.add(r);
            });
//            System.out.println();
            this.sections.add(new UserAdvertisementsViewsDateSectionResponse(date, section));
        }

    }

    private List<LocalDate> getDates(List<UserAdvertisementViewResponse> responses) {
        List<LocalDate> dates = new ArrayList<>();
        responses.forEach((r) -> {
            if (!dates.contains(r.getDate()))
                dates.add(r.getDate());
        });
        return dates;
    }

}
