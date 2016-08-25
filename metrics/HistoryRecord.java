package metrics;


import java.util.HashMap;


public class HistoryRecord {
    private int nUpdate;
    private HashMap<String, Object> parameters;
    
    public HistoryRecord(int nUpdate, HashMap<String, Object> parameters) {
        this.nUpdate = nUpdate;
        this.parameters = parameters;
    }
    
    public HashMap<String, Object> getParameters() {
        return this.parameters;
    }
    
    public int getNUpdate() {
        return this.nUpdate;
    }
}
