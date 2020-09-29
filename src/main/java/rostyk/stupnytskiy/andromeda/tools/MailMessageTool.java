package rostyk.stupnytskiy.andromeda.tools;

import org.springframework.stereotype.Component;
import rostyk.stupnytskiy.andromeda.constants.MailMessageConstants;

@Component
public class MailMessageTool {

    public String getConfirmMessage(String confirmCode) {
        return String.format(MailMessageConstants.CONFIRM_MESSAGE, confirmCode);
    }
}
