package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsAdvertisementForSearchResponse {

    private Long id;
    private String title;
    private String image;
    private String type;
    private String seller;
    private Double rating;
    private Long sellerId;
    private Long sold;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime date;


    public GoodsAdvertisementForSearchResponse(GoodsAdvertisement advertisement) {
        this.id = advertisement.getId();
        this.title = advertisement.getTitle();
        this.image = advertisement.getMainImage();
        this.seller = advertisement.getSeller().getShopName();
        this.sellerId = advertisement.getSeller().getId();
        this.sold = advertisement.getStatistics().getSold();
        this.date = advertisement.getStatistics().getCreationDate();
        this.type = advertisement.getDiscriminatorValue();
        this.rating = advertisement.getStatistics().getRating();
    }
}