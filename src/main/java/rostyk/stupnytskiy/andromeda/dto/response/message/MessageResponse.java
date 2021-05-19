package rostyk.stupnytskiy.andromeda.dto.response.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.message.Message;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {

    private Long id;
    private String text;
    private String image;
    private Long senderId;
    private String senderName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime time;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.image = message.getImage();
        this.senderId = message.getSender().getId();
        this.senderName = message.getSender().getUserName();
        this.time = message.getSendTime();
    }
}
