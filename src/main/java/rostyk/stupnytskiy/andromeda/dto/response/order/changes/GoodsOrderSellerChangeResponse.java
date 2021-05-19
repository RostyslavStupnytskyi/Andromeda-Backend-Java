package rostyk.stupnytskiy.andromeda.dto.response.order.changes;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChange;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChangeType;

@Getter
@Setter
public class GoodsOrderSellerChangeResponse {

    private GoodsOrderSellerChangeType type;
    private String valueFrom;
    private String valueTo;

    public GoodsOrderSellerChangeResponse(GoodsOrderSellerChange change) {
        this.type = change.getType();
        this.valueFrom = change.getValueFrom();
        this.valueTo = change.getValueTo();
    }
}
