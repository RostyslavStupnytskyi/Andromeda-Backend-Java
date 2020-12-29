package rostyk.stupnytskiy.andromeda.entity.order;

public enum GoodsOrderStatus {
    WAITING_FOR_PAYMENT_VERIFY,
    WAITING_FOR_SENDING,
    WAITING_FOR_DELIVERY,
    WAITING_FOR_RESPONSE,
    CLOSED,
    CANCELLED,
    WAITING_FOR_SELLER_CANCEL_CONFIRMATION
}
