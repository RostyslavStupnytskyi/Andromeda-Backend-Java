package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserAdvertisementViewRepository extends JpaRepository<UserAdvertisementView, Long> {


    Page<UserAdvertisementView> findAllByMonthStatisticsUserStatisticsOrderByDateTimeDesc(UserStatistics statistics, Pageable pageable);

    Page<UserAdvertisementView> findAllByMonthStatisticsUserStatisticsAndDateTimeBetweenOrderByDateTimeDesc(UserStatistics statistics, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);

    Page<UserAdvertisementView> findPageByMonthStatisticsUserStatistics(UserStatistics statistics,Pageable pageable);

    Optional<UserAdvertisementView> findTopByMonthStatisticsUserStatisticsOrderByIdDesc(UserStatistics statistics);

}
