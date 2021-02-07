package rostyk.stupnytskiy.andromeda.repository.statistics.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import java.time.Month;
import java.util.Optional;

@Repository
public interface GoodsAdvertisementMonthStatisticsRepository extends JpaRepository<GoodsAdvertisementMonthStatistics, Long> {

//    Optional<GoodsAdvertisementStatistics> findOneByGoodsAdvertisementId(Long id);

    Optional<GoodsAdvertisementMonthStatistics> findOneByStatisticsAndMonthAndYear(GoodsAdvertisementStatistics statistics, Month month, Integer year);
}
