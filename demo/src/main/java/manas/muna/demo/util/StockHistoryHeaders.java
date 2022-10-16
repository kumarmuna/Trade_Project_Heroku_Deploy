package manas.muna.demo.util;

public enum StockHistoryHeaders {

    DATE("Date",1),
    OPEN("Open", 2),
    HIGH("High", 3),
    LOW("Low", 4),
    CLOSE("Close", 5),
    ADJ_CLOSE("Adj Close", 6),
    VOLUME("Volume", 7);

    String name;
    int pos;
    StockHistoryHeaders(String name, int pos){
        this.name = name;
        this.pos = pos;
    }
}
