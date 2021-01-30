package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerMonthStatistics;

import java.time.Month;
import java.util.Optional;


@Repository
public interface GoodsSellerMonthStatisticsRepository extends JpaRepository<GoodsSellerMonthStatistics, Long>{

    Optional<GoodsSellerMonthStatistics> findOneBySellerStatisticsSellerIdAndMonthAndYear(Long id, Month month, Integer year);
}
