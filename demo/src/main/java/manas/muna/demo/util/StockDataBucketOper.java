package manas.muna.demo.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class StockDataBucketOper {

    public static StockDataBucket root;

    static {
//        root = new StockDataBucket();
        root = StockDataBucket.createStockData();
//        String json = StockData.getJsonData();
//        root = StockDataBucket.prepareObjectFromJsonString(json);
    }

    public static void addBucket(String stockName, List<String> historyData) {
        StockDataBucket stockDataBucket = root;
        if (stockDataBucket == null)
            stockDataBucket = new StockDataBucket(stockName, historyData);
        else{
            while (!stockDataBucket.name.equals(stockName) && stockDataBucket.next != null){
                stockDataBucket = stockDataBucket.next;
            }
            if (stockDataBucket.name.equals(stockName)) {
                stockDataBucket.historyData = historyData;
            }
            else if (stockDataBucket.next==null){
                Map<String, Stack<Double>> emaData = new HashMap<>();
                Stack<Double> ema = new Stack<>();
                ema.push(0.0);
                emaData.put("EMA9", ema);
                emaData.put("EMA30", ema);
                stockDataBucket.next = new StockDataBucket(stockName, historyData, emaData);
            }
        }
    }

    public static Map<String, Double> readPreviousDayEma(String stockName) throws Exception{
        Map<String, Double> res = new HashMap<>();
        Double[] tod = readTodaysEma(stockName);
        res.put("EMA9", tod[0]);
        res.put("EMA30", tod[1]);

        return res;
    }

    public static List<String[]> readStockHistoryData(String stockName) throws Exception{
        List<String[]> data = new ArrayList<>();
        List<String> historyData;
        StockDataBucket stockDataBucket = root;
        if (root == null) {
            System.out.println("no stock data");
            throw new Exception("no stock data");
        }else {
            while (!stockDataBucket.name.equals(stockName))
                stockDataBucket = stockDataBucket.next;
            historyData = stockDataBucket.historyData;
            for (String str: historyData){
                data.add(str.split(","));
            }
        }

        return data;
    }

    public static void addUpdateEmaData(String stockName, Map<String, Double> todayEmaData) throws Exception{
        StockDataBucket stockDataBucket = root;
        Map<String, Stack<Double>> emaData;
        if (stockDataBucket == null) {
            System.out.println("no stock data");
            throw new Exception("no stock data");
        }else {
            while (!stockDataBucket.name.equals(stockName))
                stockDataBucket = stockDataBucket.next;
            emaData = stockDataBucket.emaData;
            Stack<Double> ema = createEmaStackData(emaData.get("EMA9"), todayEmaData, "EMA9");
            emaData.put("EMA9", ema);
            ema = createEmaStackData(emaData.get("EMA30"), todayEmaData, "EMA30");
            emaData.put("EMA30", ema);
        }
    }

    private static Stack<Double> createEmaStackData(Stack<Double> ema, Map<String, Double> todayEmaData, String key) {
        Stack<Double> data = new Stack<>();
        Enumeration enu = ema.elements();
        List<Double> enuData = new ArrayList<>();
        int emaCount = ema.size();
        while(enu.hasMoreElements()){
            if (emaCount < 5)
                enuData.add((Double) enu.nextElement());
            else
                enu.nextElement();
            emaCount--;
        }
        for (Double dt:enuData){
            data.push(dt);
        }
        data.push(todayEmaData.get(key));
        return data;
    }

    public static Double[] readTodaysEma(String stockName) throws Exception{
        StockDataBucket stockDataBucket = root;
        Double[] todaysEma = null;
        Map<String, Stack<Double>> emaData;
        if (stockDataBucket == null) {
            System.out.println("no stock data");
            throw new Exception("no stock data");
        }else {
            while (!stockDataBucket.name.equals(stockName))
                stockDataBucket = stockDataBucket.next;
            emaData = stockDataBucket.emaData;
            if (emaData.size()!=0)
                todaysEma = new Double[]{emaData.get("EMA9").peek(), emaData.get("EMA30").peek()};
            else
                todaysEma = new Double[]{0.0, 0.0};
        }

        return todaysEma;
    }

    public static List<String[]> readEmaData(String stockName) throws Exception{
        StockDataBucket stockDataBucket = root;
        List<String[]> todaysEma = new ArrayList<>();
        Map<String, Stack<Double>> emaData;
        if (stockDataBucket == null) {
            System.out.println("no stock data");
            throw new Exception("no stock data");
        }else {
            while (!stockDataBucket.name.equals(stockName))
                stockDataBucket = stockDataBucket.next;
            emaData = stockDataBucket.emaData;
            String[] ema9 = readEma(emaData.get("EMA9"));
            String[] ema30 = readEma(emaData.get("EMA30"));
            for (int i=0; i<ema9.length; i++){
                todaysEma.add(new String[]{ema9[i], ema30[i]});
            }

        }
        return todaysEma;
    }

    private static String[] readEma(Stack<Double> ema) {
        Enumeration enu = ema.elements();
        List<String> res = new ArrayList<>();

        while (enu.hasMoreElements()) {
            res.add(enu.nextElement().toString());
        }

        return res.toArray(new String[res.size()]);
    }

    public static String readRoot() throws Exception{
        StockDataBucket stockDataBucket = root;
        String result = "";
        Map<String, Stack<Double>> emaData;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        if (stockDataBucket == null) {
            System.out.println("no stock data");
            throw new Exception("no stock data");
        }else {
            result = mapper.writeValueAsString(stockDataBucket);
//            while (stockDataBucket.next != null){
//                System.out.println("StockName : "+ stockDataBucket.name);
//            }
            System.out.println(stockDataBucket.emaData.get("EMA9").peek());
            System.out.println(stockDataBucket.emaData.get("EMA30").peek());
        }
        return result;
    }
}
