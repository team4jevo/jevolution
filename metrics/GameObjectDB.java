package metrics;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import gamelogic2.Organism;


public class GameObjectDB {
    private List<GameObjectRecord> records;

    public GameObjectDB() {
        this.records = new ArrayList<GameObjectRecord>();
    }

    /**
     * Add's a single game object record to database. Record will not be added
     * if it does not have unique id.
     * 
     * @param record
     *            game object record to be added.
     */
    public void addRecord(GameObjectRecord record) {
        // If record's id is unique, add it to list
        if (uniqueId(record)) {
            records.add(record);
        }
    }

    /**
     * Add's a list of game object records to database. Those records that do
     * not have unique id will not be added.
     * 
     * @param listOfRecords
     *            list containing game object records to be added.
     */
    public void addRecords(List<GameObjectRecord> listOfRecords) {
        for (GameObjectRecord record : listOfRecords) {
            if (uniqueId(record)) {
                records.add(record);
            }
        }
    }

    /**
     * Remove's game object record from database.
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
     * Return's game object record. Return's null if no such record with passed
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
     * Return's number of game object records containing game objects with
     * specified type and indicated inner state. Inner state exists for all
     * subclasses of class Organism. If specified type has no inner state,
     * returns 0.
     * 
     * @param type
     *            game object type
     * @param state
     *            game object inner state
     * @return
     */
    public int getTotalRecordsWithState(String type, boolean state) {
        int n = 0;
        for (GameObjectRecord record : records) {
            if (record.getType().equals(type)) {
                graphics.GameObject gameObject = record.getGameObject();
                // If gameObject is not an Organism, break loop
                if (!Organism.class.isInstance(gameObject)) {
                    break;
                }
                // Cast gameObject to access getState method of Organism class
                Organism organism = (Organism) gameObject;
                if (organism.getState() == state) {
                    n++;
                }
            }
        }
        return n;
    }

    /**
     * Replace's game object record with id matching the passed id with passed
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

=======

public class GameObjectDB {
    private List<GameObjectRecord> records;
    
    public GameObjectDB() {
        this.records = new ArrayList<>();
    }
    
    public void addRecord(GameObjectRecord record) {
        
    }
>>>>>>> branch 'master' of https://github.com/team4jevo/jevolution.git
}
