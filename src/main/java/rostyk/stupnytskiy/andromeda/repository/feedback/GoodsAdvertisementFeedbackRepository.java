package rostyk.stupnytskiy.andromeda.repository.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoodsAdvertisementFeedbackRepository extends JpaRepository<GoodsAdvertisementFeedback,Long> {

    List<GoodsAdvertisementFeedback> findAllByGoodsAdvertisementId(Long id);

    Page<GoodsAdvertisementFeedback> findPageByGoodsAdvertisementId(Long id, Pageable pageable);

    List<GoodsAdvertisementFeedback> findAllByGoodsAdvertisementSeller(GoodsSellerAccount seller);

}
