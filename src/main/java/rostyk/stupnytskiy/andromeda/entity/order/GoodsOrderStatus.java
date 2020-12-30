package rostyk.stupnytskiy.andromeda.entity.order;

public enum GoodsOrderStatus {
    WAITING_FOR_PAYMENT_VERIFY,
    WAITING_FOR_SENDING,
    WAITING_FOR_DELIVERY,
    WARRANTY_PERIOD,
    CLOSED,
    CANCELLED,
    WAITING_FOR_SELLER_CANCEL_CONFIRMATION
}
