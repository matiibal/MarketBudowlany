package utils.converters;

import ModelFx.BuiltItemFx;
import ModelFx.OrdersFx;
import database.models.BuiltItems;
import database.models.Orders;
import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;

public class ConverterOrder {

    @SuppressWarnings("Duplicates")
    public static Orders convertToOrder(OrdersFx orderFx)
    {
        Orders order = new Orders();
        order.setId(orderFx.getId());
       // order.setClient(ConverterClient.convertToClient(orderFx.getClientFx()));
        //order.setQuantity(orderFx.getQuantity());
        order.setTotalPrice(orderFx.getTotal());

        ArrayList<BuiltItems> builtItems = new ArrayList<>();
        ArrayList<ObjectProperty<BuiltItemFx>> builtItemFx = new ArrayList<>();


        builtItemFx = orderFx.getItems();
        //dodanie do listy builtItems po konwersji
        builtItemFx.forEach(e->
        {

            builtItems.add(ConverterBuiltItems.convertToBuiltItems(e.get()));
        });

      //  order.setBuiltItems(builtItems);

        return order;
    }
    @SuppressWarnings("Duplicates")
    public static OrdersFx convertToOrderFx(Orders order)
    {

        OrdersFx orderFx = new OrdersFx();
        orderFx.setId(order.getId());
       // orderFx.setClientFx(ConverterClient.convertToClientFx(order.getClient()));
       // orderFx.setQuantity(order.getQuantity());
        orderFx.setTotal(order.getTotalPrice());


        ArrayList<ObjectProperty<BuiltItemFx>> itemsFx = new ArrayList<>();
        ArrayList<BuiltItems>  items = new ArrayList<>();
        ArrayList<BuiltItemFx>  itemsFxAL = new ArrayList<>();

//        items = order.getBuiltItems();
//        items.forEach(e->
//        {
//
//          //  itemsFx.add(ConverterBuiltItems.convertToBuiltItemsFx(e));
//        });





       orderFx.setItems(itemsFx);


        return orderFx;
    }
}
