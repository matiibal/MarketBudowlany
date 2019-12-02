package ModelFx;


import database.dao.OrdersDao;
import database.models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.chart.*;
import utils.*;
import utils.converters.ConvertHistoryData;
import utils.converters.ConvertOrderDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrdersService {

    private OrdersDao orderDao;
    private ObservableList<BuiltItemFx> orderItemList = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> theBestSalaryList = FXCollections.observableArrayList();
    private ObservableList<HistoryDataFx> historyDataOList = FXCollections.observableArrayList();
    private ObservableList<OrderDetailsFx> orderDetailsFxObservableList = FXCollections.observableArrayList();
    private ObservableList<BarChart.Data<String, BigDecimal>> theBestClientList = FXCollections.observableArrayList();
    private ArrayList<PieChart.Data> dataTheBestSalary;
    private ArrayList<XYChart.Series> dataTheBestClient;
    public OrdersService() {
        orderDao = new OrdersDao();

    }

    public PieChart init(PieChart pieTheBestSaleProduct) {

        //dodanie do observable list
        List<PieData> items = getOrders();
      //  this.orderItemList.clear();
        dataTheBestSalary = new ArrayList<>();
        items.forEach(e->
        {
            PieChart.Data data = new PieChart.Data(e.getName(), e.getQuantity().intValue());
            dataTheBestSalary.add(data);
        });

        theBestSalaryList.addAll(dataTheBestSalary);
        pieTheBestSaleProduct.dataProperty().set(theBestSalaryList);

        return pieTheBestSaleProduct;
    }

    public List<PieData> getOrders() {
        orderDao = new OrdersDao();
        return this.findOrderSale();
    }


    public List<BarchartData> getBestClient() {
        orderDao = new OrdersDao();
        return this.findBestClient();
    }

    public List<HistoryData> getHistoryData() {
        orderDao = new OrdersDao();
        return this.findHistoryData();
    }
    public List<OrderDetails> getOrderDetails(int id) {
        orderDao = new OrdersDao();
        return this.getDetails(id);
    }

    public BarChart<String, BigDecimal> initBarChart(BarChart<String, BigDecimal> barBestClient)
    {
        //dodanie do observable list
        List<BarchartData> items = getBestClient();


        XYChart.Series series = new XYChart.Series();
        items.forEach(e->
        {
            series.getData().add(new XYChart.Data(e.getClientData(), e.getSum()));
            });

        series.setName("Suma wydatków w hurtowni budowlanej w PLN");

        barBestClient.getData().addAll(series);

        return barBestClient;
    }


    public void initHistoryData()
    {
        List<HistoryData> historyData = getHistoryData();

        historyData.forEach(e->
        {
            historyDataOList.add(ConvertHistoryData.convertToHistoryDataFx(e));
        });

    }


    public void initOrderInformation(int id)
    {
        List<OrderDetails> orderDetails = getOrderDetails(id);
        orderDetails.forEach(e->
        {
            orderDetailsFxObservableList.add(ConvertOrderDetails.convertToOrderDetailsFx(e));
        });
    }

    public void persist(Orders order) {
        //Client client = ConverterClient.convertToClient(this.getClientFxObjectProperty());
        orderDao.openCurrentSessionwithTransaction();
        orderDao.persist(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public void update() {
        //  Client entity=ConverterClient.convertToClient(this.getClientFxObjectPropertyEdit());
//        clientDao.openCurrentSessionwithTransaction();
//        clientDao.update(entity);
//        clientDao.closeCurrentSessionwithTransaction();
//        this.init();
    }

    public Orders findById(Integer id) {
        orderDao.openCurrentSession();
        Orders order = orderDao.findById(id);
        orderDao.closeCurrentSession();
        return order;
    }

    public void delete(Integer id) {
        orderDao.openCurrentSessionwithTransaction();
        Orders order = orderDao.findById(id);
        orderDao.delete(order);
        orderDao.closeCurrentSessionwithTransaction();
    }


    public void deleteOrder(int id) {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.deleteOrder(id);
        orderDao.closeCurrentSessionwithTransaction();

    }

    public List<Orders> findAll() {

        orderDao.openCurrentSession();
        List<Orders> orders = orderDao.findAll();
        orderDao.closeCurrentSession();
        return orders;
    }


    public List<PieData> findOrderSale() {

        orderDao.openCurrentSession();
        List<PieData> orders = orderDao.findOrderSale();
        orderDao.closeCurrentSession();
        return orders;
    }
    public List<BarchartData> findBestClient() {

        orderDao.openCurrentSession();
        List<BarchartData> data = orderDao.findBestClient();
        orderDao.closeCurrentSession();
        return data;
    }

    public List<HistoryData> findHistoryData(){

        orderDao.openCurrentSession();
        List<HistoryData> data = orderDao.findHistoryData();
        orderDao.closeCurrentSession();
        return data;
    }


    public List<OrderDetails> getDetails(int id){

        orderDao.openCurrentSession();
        List<OrderDetails> data = orderDao.getDetails(id);
        orderDao.closeCurrentSession();
        return data;
    }

    public void deleteAll() {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.deleteAll();
        orderDao.closeCurrentSessionwithTransaction();
    }

    public OrdersDao orderDao() {
        return orderDao;
    }


    public ObservableList<BuiltItemFx> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(ObservableList<BuiltItemFx> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public ObservableList<HistoryDataFx> getHistoryDataOList() {
        return historyDataOList;
    }

    public void setHistoryDataOList(ObservableList<HistoryDataFx> historyDataOList) {
        this.historyDataOList = historyDataOList;
    }

    public ObservableList<OrderDetailsFx> getOrderDetailsFxObservableList() {
        return orderDetailsFxObservableList;
    }

    public void setOrderDetailsFxObservableList(ObservableList<OrderDetailsFx> orderDetailsFxObservableList) {
        this.orderDetailsFxObservableList = orderDetailsFxObservableList;
    }
}
