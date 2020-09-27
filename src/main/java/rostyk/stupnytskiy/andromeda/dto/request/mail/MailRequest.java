package rostyk.stupnytskiy.andromeda.dto.request.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    private String login;
    private String password;
}
