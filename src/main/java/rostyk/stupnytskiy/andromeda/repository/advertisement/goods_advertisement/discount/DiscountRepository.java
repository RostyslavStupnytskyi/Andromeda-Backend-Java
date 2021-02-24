package rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.discount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    //    WHERE created >= ;
    @Query("select d from Discount d where d.valuesPriceCount.id = :id and d.startDate >= UTC_TIMESTAMP and d.endDate <= UTC_TIMESTAMP")
    Optional<Discount> findCurrentDiscountByValuesPriceCountId(@Param("id") Long id);

    //    @Query("select d from Discount d where d.goodsAdvertisement.id = :id and  d.startDate >= (UTC_TIMESTAMP as CurrUtcDateAndTime) and d.endDate <= (UTC_TIMESTAMP as CurrUtcDateAndTime)")
    @Query("select d from Discount d where d.goodsAdvertisement.id = :id and d.startDate >= UTC_TIMESTAMP and d.endDate <= UTC_TIMESTAMP")
    Optional<Discount> findCurrentDiscountByGoodsAdvertisementId(@Param("id") Long id);

    @Query("select case when count(d) > 0 then true else false end from Discount d where d.goodsAdvertisement.id = :id and d.startDate <= :date and d.endDate >= :date")
    boolean existsByGoodsAdvertisementIdAndStartDateAndEndDateBetween(@Param("id") Long id, @Param("date") LocalDateTime dateTime);

    @Query("select case when count(d) > 0 then true else false end from Discount d where d.valuesPriceCount.id = :id and d.startDate <= :date and d.endDate >= :date")
    boolean existsByParamsValueIdAndStartDateAndEndDateBetween(@Param("id") Long id, @Param("date") LocalDateTime dateTime);

    @Query("select case when count(d) > 0 then true else false end from Discount d where d.valuesPriceCount.id = :id and ((d.startDate >= :date1 and d.startDate <= :date2) or (d.endDate >= :date1 and d.endDate <= :date2)) ")
    boolean existsByParamsValueIdAndStartDateOrEndDateBetweenDates(@Param("id") Long id, @Param("date1") LocalDateTime dateTime1, @Param("date2") LocalDateTime dateTime2);
}
