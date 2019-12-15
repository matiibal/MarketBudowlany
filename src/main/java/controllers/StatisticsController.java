package controllers;

import ModelFx.OrdersService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
