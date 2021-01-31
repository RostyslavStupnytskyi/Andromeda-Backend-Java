package rostyk.stupnytskiy.andromeda.entity.notification;

public class NotificationMessageConstants {
//    public static final String CONFIRM_MESSAGE =
//            "<h2>Your email has been specified for registration in the Andromeda store</h2> " +
//                    "<h1>Here is your confirmation code : <font color = \" res \">%s" +
//                    "</font></h1><h3>Enter it in the confirmation field if it is you.<br>" +
//                    "<b>If you have not registered in the Andromeda store, ignore this message.</b></h3>";

    public static final String NEW_ORDER_FOR_SELLER_NOTIFICATION_MESSAGE =
            "Ви отримали нове замовлення! <a class=\"notification-link\" href=\"/client/seller/order-data?orderId=%s\"  target=\"_blank\">Переглянути</a>";

    public static final String ORDER_SEND_FOR_USER_NOTIFICATION_MESSAGE =
            "Замовлення №%s вже у дорозі! <a class=\"notification-link\" href=\"/client/user/order-data?orderId=%s\"  target=\"_blank\">Переглянути</a>";

    public static final String ORDER_RECEIVED_FOR_SELLER_NOTIFICATION_MESSAGE =
            "Покупець підтвердив отримання замовлення №%s <a class=\"notification-link\" href=\"/client/seller/order-data?orderId=%s\"  target=\"_blank\">Переглянути</a>";
}
