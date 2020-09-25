package rostyk.stupnytskiy.andromeda.dto.request.account;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountLoginRequest {

    @NotNull
    @NotBlank
    private String login;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 30)
    private String password;

}
