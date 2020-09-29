package rostyk.stupnytskiy.andromeda.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailMessageService {

//    @Autowired
//    private MailMessageConstants constants;

    public String getConfirmMessage(String confirmCode) {
        return String.format(MailMessageConstants.CONFIRM_MESSAGE, confirmCode);
    }
}
