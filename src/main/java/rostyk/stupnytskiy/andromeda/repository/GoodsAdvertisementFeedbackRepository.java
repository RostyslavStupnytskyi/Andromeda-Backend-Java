package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;

@Repository
public interface GoodsAdvertisementFeedbackRepository extends JpaRepository<GoodsAdvertisementFeedback,Long> {
}
