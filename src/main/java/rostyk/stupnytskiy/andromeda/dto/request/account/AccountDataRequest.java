package rostyk.stupnytskiy.andromeda.dto.request.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDataRequest {
    private String countryCode;

    private Long countryId;
}
