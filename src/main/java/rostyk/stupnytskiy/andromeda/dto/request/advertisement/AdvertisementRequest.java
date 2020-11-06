package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdvertisementRequest {

    private String title;

    private String description;

    private String mainImage;

    private List<String> images;

    private Long subcategoryId;

    private Integer price;

    @Override
    public String toString() {
        return "AdvertisementRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", images=" + images +
                ", subcategoryId=" + subcategoryId +
                ", price=" + price +
                '}';
    }

    public String print() {
        images.forEach(i -> i = i.substring(0, 50));
        return "Images array = [" + images + "]";
    }
}
