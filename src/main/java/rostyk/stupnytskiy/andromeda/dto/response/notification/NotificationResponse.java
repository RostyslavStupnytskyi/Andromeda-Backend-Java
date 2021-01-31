package rostyk.stupnytskiy.andromeda.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.notification.Notification;
import rostyk.stupnytskiy.andromeda.entity.notification.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponse {

    private Long id;
    private String text;
    private NotificationType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;
    private Boolean isRead;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.text = notification.getText();
        this.type = notification.getType();
        this.dateTime = LocalDateTime.of(notification.getDate(), notification.getTime());
        this.isRead = notification.getIsRead();
    }
}
