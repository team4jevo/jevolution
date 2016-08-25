package metrics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jevo.GameObject;

/**
 * Experimenting. Ignore this class for now
 * 
 * @author student
 *
 */
public class GameObjectRecord {
    private static final int MAX_HISTORY = 250;
    private static final Class<?> limitingClass = jevo.GameObject.class;
    private int nUpdate;
    private GameObject gameObject;
    private List<HistoryRecord> recordedHistory;

    public GameObjectRecord(GameObject gameObject)
            throws IllegalArgumentException, IllegalAccessException {
        this.gameObject = gameObject;
        this.nUpdate = 0;
        updateHistory();
    }

    public int getId() {
        return this.gameObject.getId();
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public String getType() {
        return this.gameObject.getGoType();
    }
    
    public List<HistoryRecord> getRecordedHistory() {
        return this.recordedHistory;
    }

    public int getNUpdate() {
        return nUpdate;
    }

    public void setNUpdate(int nUpdate) {
        this.nUpdate = nUpdate;
    }

    public HashMap<String, Object> getParameters()
            throws IllegalArgumentException, IllegalAccessException {
        HashMap<String, Object> ans = new HashMap<>();
        for (Class<?> c = this.gameObject.getClass(); limitingClass
                .isAssignableFrom(c); c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String fieldTypeName = field.getType().getSimpleName()
                        .toLowerCase();
                switch (fieldTypeName) {
                case "boolean":
                    ans.put(fieldName, (Boolean) field.get(this.gameObject));
                    break;
                case "byte":
                    ans.put(fieldName, (Byte) field.get(this.gameObject));
                    break;
                case "short":
                    ans.put(fieldName, (Short) field.get(this.gameObject));
                    break;
                case "int":
                    ans.put(fieldName, (Integer) field.get(this.gameObject));
                    break;
                case "long":
                    ans.put(fieldName, (Long) field.get(this.gameObject));
                    break;
                case "float":
                    ans.put(fieldName, (Float) field.get(this.gameObject));
                    break;
                case "double":
                    ans.put(fieldName, (Double) field.get(this.gameObject));
                    break;
                case "char":
                    ans.put(fieldName, (Character) field.get(this.gameObject));
                    break;
                case "string":
                    ans.put(fieldName, (String) field.get(this.gameObject));
                    break;
                default:
                    break;
                }
            }
        }
        return ans;
    }

    public void updateHistory() throws IllegalArgumentException, IllegalAccessException {
        if (this.recordedHistory == null) {
            recordedHistory = new ArrayList<>();
            recordedHistory.add(new HistoryRecord(nUpdate++, getParameters()));
        } else {
            if (recordedHistory.size() >= MAX_HISTORY) {
                // Remove last element if size is max size
                recordedHistory.remove(0);
            }
            recordedHistory.add(new HistoryRecord(nUpdate++, getParameters()));
        }
    }

    public static void main(String args[])
            throws IllegalArgumentException, IllegalAccessException {
        SampleSubObject1 sso = new SampleSubObject1();
        GameObjectRecord gor = new GameObjectRecord(sso);
        HashMap<String, Object> parameters = gor.getParameters();
        for (String fieldName : parameters.keySet()) {
            System.out.println(fieldName + " " + parameters.get(fieldName));
        }
    }

}
