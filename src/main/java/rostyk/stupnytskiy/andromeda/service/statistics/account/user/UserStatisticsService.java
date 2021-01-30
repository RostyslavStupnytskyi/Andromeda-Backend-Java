package rostyk.stupnytskiy.andromeda.service.statistics.account.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;
import rostyk.stupnytskiy.andromeda.repository.UserAdvertisementViewRepository;
import rostyk.stupnytskiy.andromeda.repository.UserMonthStatisticsRepository;
import rostyk.stupnytskiy.andromeda.repository.UserStatisticsRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

import java.time.LocalDateTime;
import java.time.Month;

@Service
public class UserStatisticsService {

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    @Autowired
    private UserMonthStatisticsRepository userMonthStatisticsRepository;

    @Autowired
    private UserAdvertisementViewRepository userAdvertisementViewRepository;

    @Autowired
    private UserAccountService userAccountService;


    public void createStartStatistics(UserAccount account) {
        UserStatistics statistics = account.getUserStatistics();
        saveForNewMonthStatistics(statistics);
    }
    public UserMonthStatistics saveForNewMonthStatistics(UserStatistics userStatistics) {
        UserMonthStatistics statistics = new UserMonthStatistics();
        statistics.setUserStatistics(userStatistics);
        return userMonthStatisticsRepository.save(statistics);
    }

    public UserMonthStatistics getByUserAndMonth( Month month, Integer year) {
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
        return userMonthStatisticsRepository.findOneByUserStatisticsUserAndMonthAndYear(userAccount, month, year).orElseThrow(IllegalArgumentException::new);
    }

    public UserMonthStatistics getMonthStatisticsForUserByCurrentMonth() {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        try {
            Month month = LocalDateTime.now().getMonth();
            Integer year = LocalDateTime.now().getYear();
            return userMonthStatisticsRepository.findOneByUserStatisticsUserAndMonthAndYear(user, month, year).orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            return saveForNewMonthStatistics(user.getUserStatistics());
        }
    }

}
