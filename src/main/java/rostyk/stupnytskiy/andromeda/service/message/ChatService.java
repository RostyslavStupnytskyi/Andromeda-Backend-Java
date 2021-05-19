package rostyk.stupnytskiy.andromeda.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.message.MessageRequest;
import rostyk.stupnytskiy.andromeda.dto.response.message.ChatResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.message.Chat;
import rostyk.stupnytskiy.andromeda.repository.message.ChatRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;

    public void saveMessage(Long chatId, MessageRequest request) {
        messageService.saveMessage(request, findByIdAndMember(chatId, accountService.getAccountBySecurityContextHolder()));
    }

    public Chat findByIdAndMember(Long id, Account member) {
        System.out.println(member.getUserName());
        return chatRepository.findByIdAndMember(id, member.getId()).orElse(null);
    }

    public ChatResponse getChatResponseById(Long chatId) {
        Account sender = accountService.getAccountBySecurityContextHolder();
        Chat chat = findByIdAndMember(chatId, sender);
        chat.getMembers().remove(sender);
        return new ChatResponse(chat, chat.getMembers().get(0));
    }
}
