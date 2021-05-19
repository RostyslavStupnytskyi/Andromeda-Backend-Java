package rostyk.stupnytskiy.andromeda.dto.response.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.message.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DateBlockMessagesResponse {
    private List<MessageResponse> messages;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

    public DateBlockMessagesResponse(LocalDate date, List<Message> messages) {
        this.date = date;
        this.messages = messages.stream().map(MessageResponse::new).collect(Collectors.toList());
    }
}
