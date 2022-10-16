package manas.muna.demo.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StockUtil {

//    public static String[] loadStockNames() {
//        Properties p = new Properties();
//        try {
////            Path path = Paths.get(".\\src\\main\\resources\\stock.properties");
//            String path = StockUtil.getFilePath("","stock.properties");
//            FileReader reader = new FileReader(path);
//            p.load(reader);
//            p.getProperty("stock_list");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return p.getProperty("stock_list").split(",");
//    }

    public static String[] loadStockNamesArr() {
        return new String[]{"ALKEM.NS","ASHOKLEY.NS","AXISBANK.NS","CIPLA.NS","DLF.NS","GAIL.NS","GLENMARK.NS","HINDALCO.NS","HINDCOPPER.NS","IDFCFIRSTB.NS",
        "IGL.NS","IPCALAB.NS","ITC.NS","JINDALSTEL.NS","JSWSTEEL.NS","LAURUSLABS.NS","LUPIN.NS","MARICO.NS","MGL.NS","NMDC.NS","NTPC.NS","OBEROIRLTY.NS","ONGC.NS",
        "PEL.NS","PETRONET.NS","PNB.NS","RAMCOCEM.NS","RAMCOSYS.NS","RBLBANK.NS","SAIL.NS","SBIN.NS","SYNGENE.NS","TATACONSUM.NS","TATAMOTORS.NS","TATAPOWER.NS",
        "TATASTEEL.NS","TORNTPOWER.NS","VINYLINDIA.NS","VOLTAS.NS","YESBANK.NS"};
    }

    public static String[] loadBuyStockNamesArr() {
        return new String[]{};
    }

    public static Map<String, String> readEmaData(String stockName) {
        Map<String, String> notificationData = new HashMap<>();
        try {
            int countDay = 0;
            int stockIsGreen = 0;

            List<String[]> allData = StockDataBucketOper.readEmaData(stockName);
            Collections.reverse(allData);
            for (String[] data : allData){
                if (countDay < 3){
                    if (Double.parseDouble(data[0]) > Double.parseDouble(data[1])) {
                        stockIsGreen++;
                    }
                    countDay++;
                }else
                    break;
            }
            if (stockIsGreen == 3){
                notificationData.put("stockIsGreen", "true");
                notificationData.put("stockName", stockName);
                String msg = "Stock "+stockName+" is green last 3 days, Have a look once.";
                notificationData.put("msg", msg);
                String subject = "GREEN: This is "+stockName+" Stock Alert.....";
                notificationData.put("subject", subject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notificationData;
    }

    public static Map<String, String> readEmaBuyStok(String stockName) {
        Map<String, String> notificationData = new HashMap<>();
        try {
            Double[] data = StockDataBucketOper.readTodaysEma(stockName);
            if (data[0] <= data[1]) {
                notificationData.put("stockIsRed", "true");
                notificationData.put("stockName", stockName);
                String msg = "Your Buy Stock "+stockName+"'s EMA is RED, Have a look once.";
                notificationData.put("msg", msg);
                String subject = "RED: This is "+stockName+" Stock Alert.....";
                notificationData.put("subject", subject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notificationData;
    }

//    public static String getFilePath(String folderName, String filename) throws Exception{
//        Resource resource = new ClassPathResource(folderName+"/"+filename);
//        if (StringUtils.isEmpty(folderName))
//            resource = new ClassPathResource(filename);
//        FileInputStream fis = new FileInputStream(resource.getFile());
//        Field field = fis.getClass().getDeclaredField("path");
//        field.setAccessible(true);
//        String path  = (String)field.get(fis);
//
//        return path;
//    }
//
//    public static String getDirectoryPath(String folderName) throws Exception{
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource res = resourceLoader.getResource("classpath:"+folderName);
//        InputStream inputStream = res.getInputStream();
//
//        return inputStream.toString();
//    }

}
