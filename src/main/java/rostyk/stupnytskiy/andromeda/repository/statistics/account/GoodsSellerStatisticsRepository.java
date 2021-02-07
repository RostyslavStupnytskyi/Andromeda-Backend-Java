package rostyk.stupnytskiy.andromeda.repository.statistics.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerStatistics;


@Repository
public interface GoodsSellerStatisticsRepository extends JpaRepository<GoodsSellerStatistics, Long>{

}
