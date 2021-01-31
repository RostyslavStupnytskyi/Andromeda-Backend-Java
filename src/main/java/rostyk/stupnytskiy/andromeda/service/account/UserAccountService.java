package rostyk.stupnytskiy.andromeda.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserSettingsRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;
import rostyk.stupnytskiy.andromeda.repository.UserRepository;
import rostyk.stupnytskiy.andromeda.repository.UserSettingsRepository;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.user.UserStatisticsService;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserStatisticsService userStatisticsService;

    public UserAccount findBySecurityContextHolder() {
        try {
            return findById(accountService.getIdBySecurityContextHolder());
        } catch (Exception e) {
            return null;
        }

    }

    public UserDataResponse getDataResponseForUser() {
        return new UserDataResponse(findBySecurityContextHolder());
    }

    public void save(UserAccount userAccount){
        accountService.save(userAccount);
    }

    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void changeUserSettings(UserSettingsRequest request) {
        UserAccount user = findBySecurityContextHolder();
        if (request.getCountryCode() != null)
            user.getSettings().setCountry(countryService.findCountryByCountryCode(request.getCountryCode()));
        if (request.getCurrencyCode() != null)
            user.getSettings().setCurrency(currencyService.findCurrencyByCurrencyCode(request.getCurrencyCode()));
        userSettingsRepository.save(user.getSettings());
    }

    public void addStatisticsToAll(){
        userRepository.findAll().forEach((u) -> {
            u.setUserStatistics(new UserStatistics());
            userStatisticsService.createStartStatistics(u);
        });
    }
}
