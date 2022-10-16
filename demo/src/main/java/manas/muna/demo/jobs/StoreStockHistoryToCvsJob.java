package manas.muna.demo.jobs;

import manas.muna.demo.util.StockDataBucketOper;
import manas.muna.demo.util.StockUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StoreStockHistoryToCvsJob {


    public static void main(String args[]) throws Exception{

//        https://query1.finance.yahoo.com/v7/finance/download/ITC.NS?period1=1629072000&period2=1663372800&interval=1d&events=history&includeAdjustedClose=true

       // clearHistoryFolder();
//        for (String stockName : StockUtil.loadStockNames()) {
//            System.out.println("Loading for.... "+stockName);
//            //loadStockHistoryExcel(stockName);
//        }
        execute();
    }

    public static void execute() throws Exception{
        System.out.println("StoreStockHistoryToCvsJob started.......");
//        clearHistoryFolder();
        Thread.sleep(1000);
        for (String stockName : StockUtil.loadStockNamesArr()) {
            System.out.println("Loading for.... "+stockName);
            loadStockHistoryExcel(stockName);
        }
        System.out.println("StoreStockHistoryToCvsJob end.......");
    }

//    private static void clearHistoryFolder() {
//        try {
//            String path = StockUtil.getDirectoryPath("history_data");
//            File file = new File(path);
//            Path path2 = Paths.get("${info.root-dir}\\src\\main\\resources\\history_data");
//            FileUtils.cleanDirectory(file);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    private static void loadStockHistoryExcel(String stockName) throws Exception{
        String baseUrl = "https://query1.finance.yahoo.com/v7/finance/download/";
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append(stockName);
        url.append("?");
        url.append("period1="+getEndtTime());
        url.append("&period2="+getStartTime());
        url.append("&interval=1d&events=history&includeAdjustedClose=true");

        List<String> historyData = new ArrayList<>();
        URL url1 = new URL(url.toString()); // creating a url object
        URLConnection urlConnection = url1.openConnection(); // creating a urlconnection object

        // wrapping the urlconnection in a bufferedreader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        // reading from the urlconnection using the bufferedreader
        while ((line = bufferedReader.readLine()) != null)
        {
            historyData.add(line);
        }
        bufferedReader.close();
        StockDataBucketOper.addBucket(stockName, historyData);
}

    private static Calendar getCurrentDate(){
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private static Long getStartTime() {
        Calendar calendar = getCurrentDate();
        String datePattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Date dateTime = calendar.getTime();
        String dateTimeIn24Hrs = simpleDateFormat.format(dateTime);
        System.out.println(dateTimeIn24Hrs);
        String date = dateTimeIn24Hrs;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(date, formatter);
        return dt.toEpochSecond(ZoneOffset.UTC);
    }

    private static Long getEndtTime() {
        Calendar calendar = getCurrentDate();
        String datePattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        calendar.add(Calendar.DATE, -32);
        Date dateTime = calendar.getTime();
        String dateTimeIn24Hrs = simpleDateFormat.format(dateTime);
        System.out.println(dateTimeIn24Hrs);
        String date = dateTimeIn24Hrs;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(date, formatter);
        return dt.toEpochSecond(ZoneOffset.UTC);
    }

}
