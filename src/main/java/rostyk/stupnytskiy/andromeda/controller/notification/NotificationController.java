package rostyk.stupnytskiy.andromeda.controller.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.notification.NotificationResponse;
import rostyk.stupnytskiy.andromeda.service.notification.NotificationService;

import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

//    @PutMapping
//    private void reDate() {
//        notificationService.reDating();
//    }

    @GetMapping
    private PageResponse<NotificationResponse> findPageForReceiver(PaginationRequest request) {
        return notificationService.getPageForAccount(request);
    }

    @GetMapping("/count")
    private Long getNotificationsCount() {
        return notificationService.getNotificationsCount();
    }

    @GetMapping("/last")
    private NotificationResponse getLastNotification() {
        return new NotificationResponse(notificationService.getLastNewNotification());
    }

    @PutMapping("read")
    private void makeNotificationRead(Long id) {
        notificationService.makeNotificationRead(id);
    }

    @GetMapping("date")
    private PageResponse<NotificationResponse> findPageForReceiverByDate(PaginationRequest request, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return notificationService.getPageForAccountByDate(request, localDate);
    }
}
