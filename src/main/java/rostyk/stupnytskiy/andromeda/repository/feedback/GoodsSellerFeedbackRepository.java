package rostyk.stupnytskiy.andromeda.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;

import java.util.List;

@Repository
public interface GoodsSellerFeedbackRepository extends JpaRepository<GoodsSellerFeedback,Long> {
    List<GoodsSellerFeedback> findAllBySeller(GoodsSellerAccount seller);
}
