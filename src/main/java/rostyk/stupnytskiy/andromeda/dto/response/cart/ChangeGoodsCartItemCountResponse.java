package rostyk.stupnytskiy.andromeda.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChangeGoodsCartItemCountResponse {
    private Integer count;
}
