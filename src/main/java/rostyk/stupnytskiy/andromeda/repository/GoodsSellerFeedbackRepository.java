package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;

@Repository
public interface GoodsSellerFeedbackRepository extends JpaRepository<GoodsSellerFeedback,Long> {
}
