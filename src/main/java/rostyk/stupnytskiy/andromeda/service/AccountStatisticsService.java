package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.statistics.AccountStatistics;
import rostyk.stupnytskiy.andromeda.repository.AccountStatisticsRepository;

@Service
public class AccountStatisticsService {

    @Autowired
    private AccountStatisticsRepository accountStatisticsRepository;

    @Autowired
    private AccountService accountService;

    public Boolean confirmRegistrationCode(String code) {
        AccountStatistics statistics = accountService.getAccountBySecurityContextHolder().getStatistics();
        if (statistics.getConfirmationCode().equals(code)) {
            statistics.setIsConfirmed(true);
            statistics.setConfirmationCode(null);
            accountStatisticsRepository.save(statistics);
            return true;
        }
        return false;
    }

    public void save(AccountStatistics accountStatistics){
        accountStatisticsRepository.save(accountStatistics);
    }
}
