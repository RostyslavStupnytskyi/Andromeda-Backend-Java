package rostyk.stupnytskiy.andromeda.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserSettingsRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.repository.account.user.UserAccountRepository;
import rostyk.stupnytskiy.andromeda.repository.account.user.UserSettingsRepository;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private FileTool fileTool;


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

    public void save(UserAccount userAccount) {
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
        if (request.getGetSendOrdersNotifications() != null)
            user.getSettings().setGetSendOrderNotifications(request.getGetSendOrdersNotifications());
        userSettingsRepository.save(user.getSettings());
    }

    public void changeUserData(UserDataRequest request) {
        UserAccount userAccount = findBySecurityContextHolder();
        if (request.getUsername() != null && !request.getUsername().isEmpty()) userAccount.setUsername(request.getUsername());

        if (request.getAvatar() != null) {
            try {
                userAccount.setAvatar(fileTool.saveUserAvatarImage(request.getAvatar(), userAccount.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userRepository.save(userAccount);
    }

    public void deleteUserAvatar() {
        UserAccount userAccount = findBySecurityContextHolder();

        userAccount.setAvatar(null);

        userRepository.save(userAccount);
    }
}
