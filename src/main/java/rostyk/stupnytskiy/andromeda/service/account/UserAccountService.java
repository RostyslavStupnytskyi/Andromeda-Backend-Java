package rostyk.stupnytskiy.andromeda.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserSettingsRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.repository.UserRepository;
import rostyk.stupnytskiy.andromeda.repository.UserSettingsRepository;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;

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

    public UserAccount findBySecurityContextHolder() {
        return findById(accountService.getIdBySecurityContextHolder());
    }

    public UserDataResponse getDataResponseForUser() {
        return new UserDataResponse(findBySecurityContextHolder());
    }

    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public void changeUserSettings(UserSettingsRequest request) {
        UserAccount user = findBySecurityContextHolder();
        if (request.getCountryCode() != null)
            user.getSettings().setCountry(countryService.findCountryByCountryCode(request.getCountryCode()));
        if (request.getCurrencyCode() != null)
            user.getSettings().setCurrency(currencyService.findCurrencyByCurrencyCode(request.getCurrencyCode()));
        userSettingsRepository.save(user.getSettings());
    }
}
