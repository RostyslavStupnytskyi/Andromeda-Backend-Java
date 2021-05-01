package rostyk.stupnytskiy.andromeda.repository.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsAdvertisementFeedbackRepository extends JpaRepository<GoodsAdvertisementFeedback, Long> {

    List<GoodsAdvertisementFeedback> findAllByGoodsAdvertisementId(Long id);

    Page<GoodsAdvertisementFeedback> findPageByGoodsAdvertisementId(Long id, Pageable pageable);

    List<GoodsAdvertisementFeedback> findAllByGoodsAdvertisementSeller(GoodsSellerAccount seller);

    @Query(value = "select count(*) from goods_advertisement_feedback f " +
            "where f.rating = :rating and f.goods_advertisement_id = :adv and f.creation_date >= DATE_SUB(curdate(), INTERVAL 30 DAY)",
            nativeQuery = true)
    int getCountOfFeedbacksForLast30DaysByGoodsAdvertisementAndRating(
            @Param("rating") int rating,
            @Param("adv") long advertisementId
    );

    @Query(value = "select round(avg(f.rating), 1) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv and f.creation_date >= DATE_SUB(curdate(), INTERVAL 30 DAY)",
            nativeQuery = true)
    double getAverageRatingForLast30DaysByGoodsAdvertisement(@Param("adv") long advertisementId);

    @Query(value = "select count(*) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv and f.rating = :rating and month(f.creation_date) = :_month and year(f.creation_date) = :_year",
            nativeQuery = true)
    int getCountOfFeedbacksByGoodsAdvertisementAndRatingAndMonthAndYear(
            @Param("adv") long advertisementId,
            @Param("rating") int rating,
            @Param("_month") int month,
            @Param("_year") int year
    );

    @Query(value = "select round(avg(f.rating), 1) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv and month(f.creation_date) = :_month and year(f.creation_date) = :_year",
            nativeQuery = true)
    double getAverageRatingByGoodsAdvertisementAndRatingAndMonthAndYear(
            @Param("adv") long advertisementId,
            @Param("_month") int month,
            @Param("_year") int year
    );

    @Query(value = "select count(*) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv and f.rating = :rating and year(f.creation_date) = :_year",
            nativeQuery = true)
    int getCountOfFeedbacksByGoodsAdvertisementAndRatingAndYear(
            @Param("adv") long advertisementId,
            @Param("rating") int rating,
            @Param("_year") int year
    );

    @Query(value = "select round(avg(f.rating), 1) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv and year(f.creation_date) = :_year",
            nativeQuery = true)
    Optional<Double> getAverageRatingByGoodsAdvertisementAndRatingAndYear(
            @Param("adv") long advertisementId,
            @Param("_year") int year
    );


    @Query(value = "select count(*) from goods_advertisement_feedback f " +
            "where f.rating = :rating and f.goods_advertisement_id = :adv",
            nativeQuery = true)
    int getCountOfFeedbacksByGoodsAdvertisementAndRating(
            @Param("rating") int rating,
            @Param("adv") long advertisementId
    );

    @Query(value = "select round(avg(f.rating), 1) from goods_advertisement_feedback f " +
            "where f.goods_advertisement_id = :adv",
            nativeQuery = true)
    double getAverageRatingByGoodsAdvertisement(@Param("adv") long advertisementId);
}
