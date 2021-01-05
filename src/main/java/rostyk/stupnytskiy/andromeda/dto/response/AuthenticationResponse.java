package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;

@Getter
@Setter
public class AuthenticationResponse {
    private Long id;
    private String token;
    private UserRole userRole;

    public AuthenticationResponse( String token, Long id, UserRole userRole) {
        this.token = token;
        this.id = id;
        this.userRole = userRole;
    }
}
