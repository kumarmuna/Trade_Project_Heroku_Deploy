package manas.muna.demo.jobs;

import manas.muna.demo.util.SendMail;
import manas.muna.demo.util.StockUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class StockEmaGreenStatusNotificationJob {
//    public static void main(String args[]){
//        for (String stockName : StockUtil.loadStockNames()) {
//            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
//            Map<String, String> notificationData = StockUtil.readEmaData(stockEmaDataLoad, stockName);
//            verifyAndSenfNotification(notificationData);
//        }
//    }

    public static void execute() throws Exception{
        System.out.println("StockEmaGreenStatusNotificationJob started.......");
        for (String stockName : StockUtil.loadStockNamesArr()) {
            Map<String, String> notificationData = StockUtil.readEmaData(stockName);
            verifyAndSenfNotification(notificationData);
        }
        System.out.println("StockEmaGreenStatusNotificationJob end.......");
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) throws Exception{
        if (Boolean.parseBoolean(notificationData.get("stockIsGreen"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"), notificationData.get("subject"));
        }
    }
}
