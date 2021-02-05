package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.user_account.UserSettingsRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views.UserAdvertisementsViewsResponse;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.user.UserStatisticsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserStatisticsService userStatisticsService;

    @GetMapping("/data")
    public UserDataResponse getUserDataResponse() {
        return userAccountService.getDataResponseForUser();
    }

    @PutMapping("settings")
    public void changeUserSettings(@RequestBody UserSettingsRequest request) {
        userAccountService.changeUserSettings(request);
    }

    @PutMapping("change-data")
    public void changeUserData(@RequestBody UserDataRequest request) {
        userAccountService.changeUserData(request);
    }

    @DeleteMapping("delete-avatar")
    public void deleteUserAvatar() {
        userAccountService.deleteUserAvatar();
    }

    @PostMapping
    private void add() {
        userAccountService.addStatisticsToAll();
    }

    @GetMapping("views")
    public UserAdvertisementsViewsResponse getViewsResponse(String dateFrom, String dateTo, PaginationRequest request) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate startDate = dateFrom != null ? LocalDate.parse(dateFrom, df) : null;
        LocalDate endDate = dateTo != null ? LocalDate.parse(dateTo, df) : null;

        return userStatisticsService.getViews(startDate, endDate, request);
    }
}
