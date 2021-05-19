package rostyk.stupnytskiy.andromeda.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.message.MessageRequest;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.message.Chat;
import rostyk.stupnytskiy.andromeda.entity.message.Message;
import rostyk.stupnytskiy.andromeda.repository.message.MessageRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileTool fileTool;

    public void saveMessage(MessageRequest request, Chat chat) {
        messageRepository.save(messageRequestToMessage(request, chat));
    }

    private Message messageRequestToMessage(MessageRequest request, Chat chat) {
        Message message = new Message();
        message.setChat(chat);
        message.setRead(false);
        message.setSender(accountService.getAccountBySecurityContextHolder());
        message.setText(request.getText());
        message.setSendTime(LocalDateTime.now());
        if (request.getImage() != null) {
            try {
                message.setImage(fileTool.saveUserImage(request.getImage(), message.getSender().getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
