package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;

@Repository
public interface UserAdvertisementViewRepository extends JpaRepository<UserAdvertisementView, Long> {

}
