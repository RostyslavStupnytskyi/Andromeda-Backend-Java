package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;

@Getter
@Setter
public class AuthenticationResponse {
    private String username;
    private Long id;
    private String token;
    private UserRole userRole;

    public AuthenticationResponse(String username, String token, Long id, UserRole userRole) {
        this.username = username;
        this.token = token;
        this.id = id;
        this.userRole = userRole;
    }
}
