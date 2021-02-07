package rostyk.stupnytskiy.andromeda.repository.statistics.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import java.util.Optional;

@Repository
public interface GoodsAdvertisementStatisticsRepository extends JpaRepository<GoodsAdvertisementStatistics, Long> {

    Optional<GoodsAdvertisementStatistics> findOneByGoodsAdvertisementId(Long id);
}
