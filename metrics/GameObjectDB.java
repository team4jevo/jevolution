package metrics;

import java.util.*;


public class GameObjectDB {
    private static final int MAX_HISTORY = 250;
    private List<GameObjectRecord> records;

    public GameObjectDB() {
        this.records = new ArrayList<GameObjectRecord>();
    }

    /**
     * Adds a single game object record to database. Record will not be added
     * if it does not have unique id.
     * 
     * @param record
     *            game object record to be added.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void addRecord(GameObjectRecord record)
            throws IllegalArgumentException, IllegalAccessException {
        // If record's id is unique, add it to list
        if (uniqueId(record)) {
            records.add(record);
        }
    }

    /**
     * Adds a list of game object records to database. Those records that do
     * not have unique id will not be added.
     * 
     * @param listOfRecords
     *            list containing game object records to be added.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void addRecords(List<GameObjectRecord> listOfRecords)
            throws IllegalArgumentException, IllegalAccessException {
        for (GameObjectRecord record : listOfRecords) {
            if (uniqueId(record)) {
                records.add(record);
            }
        }
    }

    /**
     * Removes game object record from database.
     * 
     * @param id
     *            id of game object record to be removed.
     */
    public void deleteRecord(int id) {
        int idx = -1;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == id) {
                idx = i;
                break;
            }
        }
        // Delete record if idx != -1
        if (idx != -1) {
            records.remove(idx);
        }
    }

    /**
     * Returns history of the indicated game objects record
     * 
     * @param recordId
     *            id of game objects record for which to return the history
     * @return list of history records
     */
    public List<HistoryRecord> getHistory(int recordId) {
        List<HistoryRecord> ans = null;
        for (GameObjectRecord record : records) {
            if (record.getId() == recordId) {
                ans = record.getRecordedHistory();
            }
        }
        return ans;
    }

    /**
     * Returns map with history of all game object records
     * 
     * @return map of history records
     */
    public HashMap<Integer, List<HistoryRecord>> getHistories() {
        HashMap<Integer, List<HistoryRecord>> ans = new HashMap<>();
        for (GameObjectRecord record : records) {
            ans.put(record.getId(), record.getRecordedHistory());
        }
        return ans;
    }

    /**
     * Returns game object record. Returns null if no such record with passed
     * id exists.
     * 
     * @param id
     *            id of game object record to be returned.
     * @return game object record
     */
    public GameObjectRecord getRecord(int id) {
        for (GameObjectRecord rec : records) {
            if (rec.getId() == id) {
                return rec;
            }
        }
        return null;
    }

    public int getTotalRecords() {
        return this.records.size();
    }

    public int getTotalRecords(String type) {
        int n = 0;
        for (GameObjectRecord record : records) {
            if (record.getType().equals(type)) {
                n++;
            }
        }
        return n;
    }

    /**
     * Replaces game object record with id matching the passed id with passed
     * game object record. No record will be deleted if no such record exists
     * with id that equals passed id.
     * 
     * @param id
     *            id of game object record to be replaced.
     * @param newRecord
     *            game object record to be placed instead.
     */
    public void replaceRecord(int id, GameObjectRecord newRecord) {
        int idx = -1;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == id) {
                idx = i;
                break;
            }
        }
        // Delete record if idx != -1
        if (idx != -1) {
            records.set(idx, newRecord);
        }
    }

    private boolean uniqueId(GameObjectRecord record) {
        for (GameObjectRecord rec : records) {
            if (rec.getId() == record.getId()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException {
        Random rand = new Random();
        jevosim.CreatureDependant cd = new jevosim.CreatureDependant(1, 1);
        GameObjectRecord gor = new GameObjectRecord(cd);
        GameObjectDB godb = new GameObjectDB();
        godb.addRecord(gor);
        List<HistoryRecord> history = godb.getRecord(cd.getId()).getRecordedHistory();
        for (HistoryRecord histRec : history) {
            System.out.println(histRec.getNUpdate() + " " + histRec.getParameters());
        }
        for (int i = 0; i < 300; i++) {
            cd.setLogicX(rand.nextInt(10));
            cd.setState(rand.nextBoolean());
            GameObjectRecord rec = godb.getRecord(cd.getId());
            rec.updateHistory();
            //godb.replaceRecord(id, newRecord);
            
        }
        history = godb.getRecord(cd.getId()).getRecordedHistory();
        for (HistoryRecord histRec : history) {
            System.out.println(histRec.getNUpdate() + " " + histRec.getParameters());
        }
    }

}
