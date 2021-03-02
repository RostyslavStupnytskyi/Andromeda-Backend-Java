package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.seller_categories;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class GoodsSellerAdvertisementCategoryRequest {

    private String title;

    private Long parentId;

}
