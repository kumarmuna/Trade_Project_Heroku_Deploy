package manas.muna.demo.jobs;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import manas.muna.demo.util.StockDataBucketOper;
import manas.muna.demo.util.StockUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadingExcelAndCalculateEMAJob {
//    public static void main(String args[]) throws Exception{
//        for (String stockName : StockUtil.loadStockNames()) {
//            System.out.print("Loading for.... "+stockName);
//            Resource resource = new ClassPathResource("history_data/"+stockName+".csv");
//            FileInputStream fis = new FileInputStream(resource.getFile());
//            Field field = fis.getClass().getDeclaredField("path");
//            field.setAccessible(true);
//            String path  = (String)field.get(fis);
//            Map<String, Double> todaysEMA = readCVSData(path, 51.83, 51.90);
//            //storeTodaysEma("D:\\share-market\\history_ema_data\\"+stockName+".csv", todaysEMA);
//        }
//    }

    public static void execute() throws Exception{
        System.out.println("ReadingExcelAndCalculateEMAJob started.......");
        for (String stockName : StockUtil.loadStockNamesArr()) {
            System.out.println("Loading for.... "+stockName);

            Map<String, Double> yesterdayEMA = StockDataBucketOper.readPreviousDayEma(stockName);
            Map<String, Double> todaysEMA = readCVSData(stockName, yesterdayEMA.get("EMA30"), yesterdayEMA.get("EMA9"));

            storeTodaysEma(stockName, todaysEMA);
        }
        System.out.println("ReadingExcelAndCalculateEMAJob end.......");
    }

    private static void storeTodaysEma(String stockName, Map<String, Double> todaysEMA) throws Exception{
        double ema9 = todaysEMA.get("todaysEMA9");
        double ema30 = todaysEMA.get("todaysEMA30");
        Map<String, Double> todayEmaData;

        try {
             todayEmaData = new HashMap<>();
             todayEmaData.put("EMA9", ema9);
             todayEmaData.put("EMA30", ema30);
             StockDataBucketOper.addUpdateEmaData(stockName, todayEmaData);

        }catch (Exception e){
            throw new Exception();
        }
    }

    private static Map<String, Double> readCVSData(String stockName, double prevDayEma30, double prevDayEma9) {
        double ema30 = 0;
        double ema9 = 0;
        double multiplier30 = 2.0/(30+1);
        double multiplier9 = 2.0/(9+1);
        Map<String, Double> todaysEMA = new HashMap<>();
        try {
            List<String[]> allData = StockDataBucketOper.readStockHistoryData(stockName);
            Collections.reverse(allData);
            //4th index column is Close
            int count = 0;
            double sum = 0;

            for (int i=0; i< allData.size()-1; i++) {
                String[] row = allData.get(i);
                if (count <= 30){
                //System.out.print(row[4]);
                sum = sum + Double.parseDouble(row[4]);
                if (count == 9){
                    ema9 = Double.parseDouble(allData.get(0)[4]) * multiplier9 + prevDayEma9 * (1-multiplier9);
                }
                count++;
                }else
                    break;
            }
            ema30 = Double.parseDouble(allData.get(0)[4]) * multiplier30 + prevDayEma30 * (1-multiplier30);
            todaysEMA.put("todaysEMA30", ema30);
            todaysEMA.put("todaysEMA9", ema9);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return todaysEMA;
    }
}
