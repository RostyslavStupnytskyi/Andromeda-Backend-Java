package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.message.MessageRequest;
import rostyk.stupnytskiy.andromeda.dto.response.message.ChatResponse;
import rostyk.stupnytskiy.andromeda.service.message.ChatService;
import rostyk.stupnytskiy.andromeda.service.message.MessageService;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    private void saveMessage(Long chatId, @RequestBody MessageRequest messageRequest) {
        chatService.saveMessage(chatId, messageRequest);
    }

    @GetMapping()
    private ChatResponse getChat(Long id) {
        return chatService.getChatResponseById(id);
    }
}
