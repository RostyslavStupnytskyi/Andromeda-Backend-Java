package rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor

@Entity
public class GoodsSellerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime registrationDate;


    @OneToMany(mappedBy = "sellerStatistics")
    private List<GoodsSellerMonthStatistics> monthStatistics = new ArrayList<>();

    @OneToOne(mappedBy = "statistics")
    private GoodsSellerAccount seller;

    public GoodsSellerStatistics() {
        registrationDate = LocalDateTime.now();
    }
//    private

}
