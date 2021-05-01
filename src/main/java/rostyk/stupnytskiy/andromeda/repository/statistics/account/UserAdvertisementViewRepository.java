package rostyk.stupnytskiy.andromeda.repository.statistics.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;

import java.time.Month;

@Repository
public interface UserAdvertisementViewRepository extends JpaRepository<UserAdvertisementView,Long> {

    @Query("select count(*) from UserAdvertisementView v where goodsAdvertisement = :adv and month(v.dateTime) = :_month and year(v.dateTime) = :_year ")
    int findCountOfViewsByAdvertisementAndMonthAndYear(
            @Param("adv") GoodsAdvertisement advertisement,
            @Param("_year") int year,
            @Param("_month") int month
    );
}
