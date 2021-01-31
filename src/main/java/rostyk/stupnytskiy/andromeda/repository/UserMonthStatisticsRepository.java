package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;

import java.time.Month;
import java.util.Optional;

@Repository
public interface UserMonthStatisticsRepository extends JpaRepository<UserMonthStatistics, Long> {

    Optional<UserMonthStatistics> findOneByUserStatisticsUserAndMonthAndYear(UserAccount userAccount, Month month, Integer year);
    Optional<UserMonthStatistics> findOneByUserStatisticsAndMonthAndYear(UserStatistics statistics, Month month, Integer year);
}
