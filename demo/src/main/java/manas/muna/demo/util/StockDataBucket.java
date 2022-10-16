package manas.muna.demo.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class StockDataBucket {
    String name;
    List<String> historyData;
    Map<String, Stack<Double>> emaData;
    StockDataBucket next;

    StockDataBucket(String stockName, List<String> historyData){
        this.name = stockName;
        this.historyData = historyData;
        this.next = null;
        this.emaData = null;
    }

    StockDataBucket(String stockName, List<String> historyData, Map<String, Stack<Double>> emaData){
        this.name = stockName;
        this.historyData = historyData;
        this.emaData = emaData;
        this.next = null;
    }

    StockDataBucket(String stockName, Map<String, Stack<Double>> emaData){
        this.name = stockName;
        this.historyData = null;
        this.emaData = emaData;
        this.next = null;
    }

    StockDataBucket(){
        Map<String, Stack<Double>> emaData = new HashMap<>();
        Stack<Double> st = new Stack<>();
        List<String> historyData = new ArrayList<>();
        st.push(3.0);
        st.push(2.0);
        st.push(1.0);
        historyData.add("Date,Open,High,Low,Close,Adj Close,Volume");
        historyData.add("2022-09-07,164.500000,164.750000,159.800003,161.949997,161.949997,17416562");
        emaData.put("EMA9", st);
        st = new Stack<>();
        st.push(0.0);
        st.push(1.0);
        st.push(0.0);
        emaData.put("EMA30", st);
        this.name = "Manas";
        this.emaData = emaData;
        this.historyData = historyData;
        this.next = createMunaStock();
    }

    public static StockDataBucket prepareObjectFromJsonString(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        StockDataBucket res = null;
        try {
            res = mapper.readValue(json, StockDataBucket.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    private StockDataBucket createMunaStock() {
        Map<String, Stack<Double>> emaData = new HashMap<>();
        Stack<Double> st = new Stack<>();
        List<String> historyData = new ArrayList<>();
        st.push(0.0);
        st.push(1.0);
        st.push(0.0);
        historyData.add("Date,Open,High,Low,Close,Adj Close,Volume");
        historyData.add("2022-09-07,164.500000,164.750000,159.800003,161.949997,161.949997,17416562");
        emaData.put("EMA9", st);
        st = new Stack<>();
        st.push(3.0);
        st.push(2.0);
        st.push(1.0);
        emaData.put("EMA30", st);
        StockDataBucket muna = new StockDataBucket("Muna", historyData, emaData);

        return muna;
    }

    public static StockDataBucket createStockData() {
        StockDataBucket root = new StockDataBucket("ALKEM.NS", createEmaData(3138.43, 3224.98));
        root.next = new StockDataBucket("ASHOKLEY.NS", createEmaData(148.45, 154.22));
        root.next.next = new StockDataBucket("AXISBANK.NS", createEmaData(796.35, 780.80));
        root.next.next.next = new StockDataBucket("CIPLA.NS", createEmaData(1115.85, 1122.04));
        root.next.next.next.next = new StockDataBucket("DLF.NS", createEmaData(360.25, 365.44));
        root.next.next.next.next.next = new StockDataBucket("GAIL.NS", createEmaData(85.67, 86.11));
        root.next.next.next.next.next.next = new StockDataBucket("GLENMARK.NS", createEmaData(391.64, 390.44));
        root.next.next.next.next.next.next.next = new StockDataBucket("HINDALCO.NS", createEmaData(399.62, 398.68));
        root.next.next.next.next.next.next.next.next = new StockDataBucket("HINDCOPPER.NS", createEmaData(108.34, 110.33));
        root.next.next.next.next.next.next.next.next.next = new StockDataBucket("ICICIBANK.NS", createEmaData(865.63, 882.17));
        root.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("IDFCFIRSTB.NS", createEmaData(54.68, 53.96));
        root.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("IGL.NS", createEmaData(377.55, 391.77));
        root.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("IPCALAB.NS", createEmaData(896.72, 893.12));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("ITC.NS", createEmaData(329.80, 336.21));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("JINDALSTEL.NS", createEmaData(437.11, 439.5));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("JSWSTEEL.NS", createEmaData(646.03, 655.89));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("LAURUSLABS.NS", createEmaData(509.25, 512.30));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("LUPIN.NS", createEmaData(694.96, 693.32));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("MARICO.NS", createEmaData(510.99, 523.29));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("MGL.NS", createEmaData(789.19, 820.66));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("NMDC.NS", createEmaData(132.11, 131.82));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("NTPC.NS", createEmaData(164.14, 165.15));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("OBEROIRLTY.NS", createEmaData(897.33, 936.91));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("ONGC.NS", createEmaData(130.14, 129.37));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("PEL.NS", createEmaData(798.94, 836.55));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("PETRONET.NS", createEmaData(199.19, 200.24));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("PNB.NS", createEmaData(35.80, 37.17));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("RAMCOCEM.NS", createEmaData(703.49, 734.35));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("RAMCOSYS.NS", createEmaData(258.42, 262.47));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("RBLBANK.NS", createEmaData(122.80, 123.45));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("SAIL.NS", createEmaData(78.95, 79.18));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("SBIN.NS", createEmaData(525.06, 538.09));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("SYNGENE.NS", createEmaData(553.95, 556.60));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("TATACONSUM.NS", createEmaData(754.81, 776.41));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("TATAMOTORS.NS", createEmaData(394.71, 400.35));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("TATAPOWER.NS", createEmaData(215.87, 219.24));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("TATASTEEL.NS", createEmaData(100.37, 101.16));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("TORNTPOWER.NS", createEmaData(476.69, 492.96));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("VINYLINDIA.NS", createEmaData(577.27, 676.57));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("VOLTAS.NS", createEmaData(874.09, 882.94));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("WIPRO.NS", createEmaData(389.74, 394.44));
        root.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next = new StockDataBucket("YESBANK.NS", createEmaData(15.93, 16.29));

        return root;
    }

    private static Map<String, Stack<Double>> createEmaData(double ema1, double ema2) {
        Map<String, Stack<Double>> emaData = new HashMap<>();
        Stack<Double> ema9 = new Stack<>();
        ema9.push(ema1);
        Stack<Double> ema30 = new Stack<>();
        ema30.push(ema2);
        emaData.put("EMA9", ema9);
        emaData.put("EMA30", ema30);

        return emaData;
    }
}
