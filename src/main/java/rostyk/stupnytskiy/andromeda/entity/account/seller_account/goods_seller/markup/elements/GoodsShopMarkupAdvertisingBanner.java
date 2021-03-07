package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsShopMarkupAdvertisingBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private GoodsShopMarkupElement element;

    @ElementCollection
    private List<String> images = new ArrayList<>();
}
