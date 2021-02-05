package rostyk.stupnytskiy.andromeda.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.notification.NotificationResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.notification.Notification;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.repository.NotificationRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static rostyk.stupnytskiy.andromeda.entity.notification.NotificationMessageConstants.*;
import static rostyk.stupnytskiy.andromeda.entity.notification.NotificationType.*;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AccountService accountService;

//    public void reDating() {
//        notificationRepository.findAll().forEach((n) -> {
//            n.setDate(n.getDateTime().toLocalDate());
//            n.setTime(n.getDateTime().toLocalTime());
//            notificationRepository.save(n);
//        });
//    }


    public PageResponse<NotificationResponse> getPageForAccount(PaginationRequest request) {
        Account account = accountService.getAccountBySecurityContextHolder();
        final Page<Notification> page = notificationRepository.findPageByReceiverOrderByIdDesc(account, request.mapToPageable());

        return new PageResponse<>(page
                .get()
                .map(NotificationResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public PageResponse<NotificationResponse> getPageForAccountByDate(PaginationRequest request, LocalDate localDate) {
        Account account = accountService.getAccountBySecurityContextHolder();
        final Page<Notification> page = notificationRepository.findPageByReceiverAndDate(account, request.mapToPageable(), localDate);

        return new PageResponse<>(page
                .get()
                .map(NotificationResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public void createNewOrderNotification(GoodsOrder order) {
        if (order.getSeller().getSettings().getSendNewOrderNotifications()) {
            Notification notification = new Notification();
            notification.setType(NEW_ORDER_NOTIFICATION);
            notification.setReceiver(order.getSeller());
            notification.setText(String.format(NEW_ORDER_FOR_SELLER_NOTIFICATION_MESSAGE, order.getId()));
            notificationRepository.save(notification);
        }
    }

    public void createOrderSendNotification(GoodsOrder order) {
        if (order.getUser().getSettings().getGetSendOrderNotifications()) {
            Notification notification = new Notification();
            notification.setType(ORDER_SEND_NOTIFICATION);
            notification.setReceiver(order.getUser());
            notification.setText(String.format(ORDER_SEND_FOR_USER_NOTIFICATION_MESSAGE, String.format("%09d", order.getId()), order.getId()));
            notificationRepository.save(notification);
        }
    }

    public void createOrderReceivedNotification(GoodsOrder order) {
        if (order.getSeller().getSettings().getSendOrderReceivedNotifications()) {
            Notification notification = new Notification();
            notification.setType(ORDER_RECEIVED_NOTIFICATION);
            notification.setReceiver(order.getSeller());
            notification.setText(String.format(ORDER_RECEIVED_FOR_SELLER_NOTIFICATION_MESSAGE, String.format("%09d", order.getId()), order.getId()));
            notificationRepository.save(notification);
        }
    }


    public void makeNotificationRead(Long id) {
        Notification notification = findByIdAndAccount(id);
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    private Notification findByIdAndAccount(Long id) {
        Account account = accountService.getAccountBySecurityContextHolder();
        return notificationRepository.findByReceiverAndId(account, id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Notification> findAllByReceiver() {

        return notificationRepository.findAllByReceiver(accountService.getAccountBySecurityContextHolder());
    }

    public Notification getLastNewNotification() {
        return notificationRepository.findLastByReceiver(accountService.getAccountBySecurityContextHolder());
    }


    public Long getNotificationsCount() {
        return findAllByReceiver().stream().filter((n) -> !n.getIsRead()).count();
    }
}
