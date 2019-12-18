package utils.converters;


import utils.OrderDetails;
import utils.OrderDetailsFx;

public class ConvertOrderDetails {

    @SuppressWarnings("Duplicates")
    public static OrderDetails convertToOrderDetails(OrderDetailsFx orderDetailsFx)
    {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setNameProduct(orderDetailsFx.getNameProduct());
        orderDetails.setQty(Integer.parseInt(orderDetailsFx.getQuantity()));
        return orderDetails;
    }
    @SuppressWarnings("Duplicates")
    public static OrderDetailsFx convertToOrderDetailsFx(OrderDetails orderDetails)
    {
        OrderDetailsFx orderDetailsFx = new OrderDetailsFx();
        orderDetailsFx.setNameProduct(orderDetails.getNameProduct());
        orderDetailsFx.setQuantity(String.valueOf(orderDetails.getQty()));

        return orderDetailsFx;
    }


}
