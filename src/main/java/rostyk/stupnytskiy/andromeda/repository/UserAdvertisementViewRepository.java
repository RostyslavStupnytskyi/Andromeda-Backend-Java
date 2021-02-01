package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;

import java.time.LocalDate;

@Repository
public interface UserAdvertisementViewRepository extends JpaRepository<UserAdvertisementView, Long> {

    Page<UserAdvertisementView> findAllByMonthStatisticsUserStatisticsOrderByDateDescTimeDesc(UserStatistics statistics, Pageable pageable);

    Page<UserAdvertisementView> findAllByMonthStatisticsUserStatisticsAndDateOrderByDateDescTimeDesc(UserStatistics statistics, LocalDate date, Pageable pageable);

    Page<UserAdvertisementView> findAllByMonthStatisticsUserStatisticsAndDateBetweenOrderByDateDescTimeDesc(UserStatistics statistics,  LocalDate dateFrom, LocalDate dateTo, Pageable pageable);
}
