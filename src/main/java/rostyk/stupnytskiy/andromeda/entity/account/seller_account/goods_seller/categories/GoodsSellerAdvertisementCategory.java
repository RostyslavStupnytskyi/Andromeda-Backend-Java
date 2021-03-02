package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

import javax.persistence.*;
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

    @ManyToOne
    private GoodsSellerAdvertisementCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<GoodsSellerAdvertisementCategory> childCategories;

    @ManyToOne
    private GoodsSellerAccount goodsSeller;
}
