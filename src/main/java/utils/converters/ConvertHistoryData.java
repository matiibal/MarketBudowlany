package utils.converters;


import utils.HistoryData;
import utils.HistoryDataFx;

import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Date.valueOf;

public class ConvertHistoryData {

    public static HistoryData convertToHistoryData(HistoryDataFx historyDataFx)
    {

        HistoryData historyData = new HistoryData();
        historyData.setId(historyDataFx.getId());
       historyData.setSecondName(historyDataFx.getSurnameColumn());
       historyData.setOrderDate(Date.valueOf(historyDataFx.getDateColumn()));
       historyData.setNip(historyDataFx.getNipColumn());
       historyData.setTotalPrice(Double.parseDouble(historyDataFx.getTotalColumn()));

        return historyData;
    }
    @SuppressWarnings("Duplicates")
    public static HistoryDataFx convertToHistoryDataFx(HistoryData historyData)
    {
        HistoryDataFx historyDataFx = new HistoryDataFx();
        historyDataFx.setId(historyData.getId());
        historyDataFx.setSurnameColumn(historyData.getSecondName());
        historyDataFx.setDateColumn(String.valueOf(historyData.getOrderDate()));
        historyDataFx.setNipColumn(historyData.getNip());
        historyDataFx.setTotalColumn(String.valueOf(historyData.getTotalPrice()));

        return historyDataFx;
    }

}
