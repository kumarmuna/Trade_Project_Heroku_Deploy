package manas.muna.demo.controller;

import manas.muna.demo.jobs.ReadingExcelAndCalculateEMAJob;
import manas.muna.demo.jobs.StockEmaGreenStatusNotificationJob;
import manas.muna.demo.jobs.StockEmaRedNotificationForBuyStockJob;
import manas.muna.demo.jobs.StoreStockHistoryToCvsJob;
import manas.muna.demo.util.StockDataBucketOper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class TDControler {

    @RequestMapping("/readHistory")
    public String readHistoryAndWriteToCvs(){
        String result = "";
        try {
            StoreStockHistoryToCvsJob.execute();
            result = "Read History Sucessfully";
        }catch (Exception e){
            result = "Error during read history data "+e.getMessage();
        }

        return result;
    }

    @RequestMapping("/calculateEma")
    public String calculateEma(){
        String result = "";
        try {
            ReadingExcelAndCalculateEMAJob.execute();
            result = "Calculated and Stored EMA";
        }catch (Exception e){
            result = "Error during EMA of data "+e.getMessage();
        }
        return result;
    }

    @RequestMapping("/sendRedNotification")
    public String sendRedNotification(){
        String result = "";
        try {
            StockEmaRedNotificationForBuyStockJob.execute();
            result = "Send red notification";
        }catch (Exception e){
            result = "Error during send red notification "+e.getMessage();
        }
        return result;
    }

    @RequestMapping("/sendGreenNotification")
    public String sendGreenNotification(){
        String result = "";
        try {
            StockEmaGreenStatusNotificationJob.execute();
            result = "Send green notification";
        }catch (Exception e){
            //result = "Error during send green notification "+e.getMessage();
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/checkData")
    public String checkStockData(){
        String result = "";
        try {
            result = StockDataBucketOper.readRoot();
        }catch (Exception e){
            result = "Error during data read "+e.getMessage();
        }
        return result;
    }
}
