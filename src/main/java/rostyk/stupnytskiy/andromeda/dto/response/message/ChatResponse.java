package rostyk.stupnytskiy.andromeda.dto.response.message;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.message.Chat;
import rostyk.stupnytskiy.andromeda.entity.message.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatResponse {

    private Long id;
    private List<DateBlockMessagesResponse> dayBlocks;
    private String username;
    private Long userId;

    public ChatResponse(Chat chat, Account interlocutor) {
        this.id = chat.getId();
        this.dayBlocks = sortMessagesToDateBlockMessagesResponses(chat.getMessages());
        this.username = interlocutor.getUserName();
        this.userId = interlocutor.getId();
    }

    public List<DateBlockMessagesResponse> sortMessagesToDateBlockMessagesResponses(List<Message> messages) {
        List<DateBlockMessagesResponse> days = new ArrayList<>();

        messages = messages.stream().sorted(Comparator.comparing(Message::getSendTime)).collect(Collectors.toList());

        List<Message> reservedList = new ArrayList<>();

        LocalDate date = messages.get(0).getSendTime().toLocalDate();

        Iterator<Message> iterator = messages.listIterator();
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getSendTime().toLocalDate().equals(date)) {
                reservedList.add(message);
            } else {
                days.add(new DateBlockMessagesResponse(date, reservedList));
                date = message.getSendTime().toLocalDate();
                reservedList.clear();
                reservedList.add(message);
            }
            if (!iterator.hasNext()) {
                days.add(new DateBlockMessagesResponse(date, reservedList));
            }
        }
        return days;
    }
}
