package manas.muna.demo.jobs;

import manas.muna.demo.util.SendMail;
import manas.muna.demo.util.StockUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class StockEmaRedNotificationForBuyStockJob {
//    public static void main(String args[]) {
//        for (String stockName : StockUtil.loadBuyStockNames()) {
//            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
//            Map<String, String> notificationData = StockUtil.readEmaBuyStok(stockEmaDataLoad, stockName);
//            verifyAndSenfNotification(notificationData);
//        }
//    }

    public static void execute() throws Exception{
        System.out.println("StockEmaRedNotificationForBuyStockJob started.......");
        for (String stockName : StockUtil.loadBuyStockNamesArr()) {
            Map<String, String> notificationData = StockUtil.readEmaBuyStok(stockName);
            verifyAndSenfNotification(notificationData);
        }
        System.out.println("StockEmaRedNotificationForBuyStockJob end.......");
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) throws Exception{
        if (Boolean.parseBoolean(notificationData.get("stockIsRed"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"), notificationData.get("subject"));
        }
    }
}
