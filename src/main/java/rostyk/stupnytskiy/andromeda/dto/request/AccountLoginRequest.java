package rostyk.stupnytskiy.andromeda.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountLoginRequest {

    @NotBlank
    private String login;

    @Size(min = 3, max = 30)
    private String password;

}
