package rostyk.stupnytskiy.andromeda.service.statistics.account.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views.UserAdvertisementViewResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views.UserAdvertisementsViewsResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;
import rostyk.stupnytskiy.andromeda.repository.UserAdvertisementViewRepository;
import rostyk.stupnytskiy.andromeda.repository.UserMonthStatisticsRepository;
import rostyk.stupnytskiy.andromeda.repository.UserStatisticsRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;

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

    public UserMonthStatistics getByUserAndMonth(Month month, Integer year) {
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
//        return userMonthStatisticsRepository.findOneByUserStatisticsUserAndMonthAndYear(userAccount, month, year).orElseThrow(IllegalArgumentException::new);
        return userMonthStatisticsRepository.findOneByUserStatisticsAndMonthAndYear(userAccount.getUserStatistics(), month, year).orElseThrow(IllegalArgumentException::new);
    }

    public UserMonthStatistics getMonthStatisticsForUserByCurrentMonth() {
        try {
            Month month = LocalDateTime.now().getMonth();
            Integer year = LocalDateTime.now().getYear();

            return getByUserAndMonth(month, year);
        } catch (IllegalArgumentException e) {
            UserAccount user = userAccountService.findBySecurityContextHolder();
            return saveForNewMonthStatistics(user.getUserStatistics());
        }
    }


    public void saveUserAdvertisementViewRequest(GoodsAdvertisement advertisement) {
        UserAdvertisementView view = new UserAdvertisementView();
        view.setGoodsAdvertisement(advertisement);
        UserAccount user = userAccountService.findBySecurityContextHolder();
        if (user != null) {
            view.setMonthStatistics(getMonthStatisticsForUserByCurrentMonth());
        }
        userAdvertisementViewRepository.save(view);

    }

    public UserAdvertisementsViewsResponse getViews(LocalDate startDate, LocalDate endDate, PaginationRequest request) {

        UserAccount userAccount = userAccountService.findBySecurityContextHolder();

        final Page<UserAdvertisementView> page;

        if (startDate != null && endDate != null) {
            page = userAdvertisementViewRepository.findAllByMonthStatisticsUserStatisticsAndDateBetweenOrderByDateDescTimeDesc(userAccount.getUserStatistics(), startDate, endDate, request.mapToPageable());
        } else if (startDate != null) {
            page = userAdvertisementViewRepository.findAllByMonthStatisticsUserStatisticsAndDateOrderByDateDescTimeDesc(userAccount.getUserStatistics(), startDate, request.mapToPageable());
        } else {
            page = userAdvertisementViewRepository.findAllByMonthStatisticsUserStatisticsOrderByDateDescTimeDesc(userAccount.getUserStatistics(), request.mapToPageable());
        }

        return new UserAdvertisementsViewsResponse(new PageResponse<>(
                page.get()
                        .map(UserAdvertisementViewResponse::new)
                        .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }
}
