package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserSettingsRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserDataResponse;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/data")
    public UserDataResponse getUserDataResponse() {
        return userAccountService.getDataResponseForUser();
    }

    @PutMapping("settings")
    public void changeUserSettings(@RequestBody UserSettingsRequest request) {
        userAccountService.changeUserSettings(request);
    }

    @PostMapping
    private void add() {
        userAccountService.addStatisticsToAll();
    }
}
