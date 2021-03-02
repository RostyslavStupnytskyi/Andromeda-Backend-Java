package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsSellerAdvertisementCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAdvertisementCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<GoodsSellerAdvertisementCategory> childrenCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<GoodsAdvertisement> advertisements;

    @ManyToOne
    private GoodsSellerAccount goodsSeller;

    public boolean isRootLevelCategory() {
        return this.parentCategory == null;
    }

    public boolean hasChildren() {
        return this.childrenCategories.size() > 0;
    }
}
