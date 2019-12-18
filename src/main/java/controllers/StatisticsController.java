package controllers;

import ModelFx.OrdersService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;

public class StatisticsController {
    @FXML
    private PieChart pieTheBestSaleProduct;
    @FXML
    private BarChart<String, BigDecimal> barChartBestClient;
    private OrdersService ordersService;

    public void initialize()
    {
        ordersService = new OrdersService();
        this.ordersService.init(pieTheBestSaleProduct);
        this.ordersService.initBarChart(barChartBestClient);
    }

    public void showProcentPieChart(MouseEvent mouseEvent) {

    }
}
