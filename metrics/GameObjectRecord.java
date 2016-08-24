package metrics;

import java.lang.reflect.Field;
import java.util.ArrayList;

import jevo.GameObject;

/**
 * Experimenting. Ignore this class for now
 * @author student
 *
 */
public class GameObjectRecord {
    private static long n = 0;
    private long id;
    private GameObject gameObject;
    private Field[] fields;
    private ArrayList<Field> fieldsList;
    
    public GameObjectRecord(GameObject gameObject) {
        this.gameObject = gameObject;
        this.id = ++GameObjectRecord.n;
        this.fields = gameObject.getClass().getDeclaredFields();
        this.fieldsList = new ArrayList<Field>();
    }
    
    public ArrayList<Field> getFieldsList() {
        return this.fieldsList;
    }
    
    public long getId() {
        return this.id;
    }
    
    public GameObject getGameObject() {
        return this.gameObject;
    }
    
    public void printFields() {
        System.out.println(this.fields.length);
        for (Field field : fields) {
            System.out.println(field.getName() + " " + field.getType().getSimpleName());
        }
    }
    
    public static void main(String args[]) {
        GameObject object1 = new customgl.LocalCreature("LC", 1, 2);
        GameObject object2 = new customgl.CreatureSimple(2, 2);
        GameObjectRecord gor = new GameObjectRecord(object1);
        GameObjectRecord gor2 = new GameObjectRecord(object2);
        
        /*
        gor.printFields();
        gor2.printFields();
        Field[] fields = object1.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + " " + field.getType().getSimpleName());
        }
        */
        
        Object obj = new Object();
        /*
        gor.printFieldsRecursively(object2);
        ArrayList<Field> fields = gor.getFieldsList();
        for (Field field : fields) {
            System.out.println(field);
        }
        */
        gor.printPrivateFields(object2.getClass());
        //gor.printClasses(object2.getClass());

    }
    
    /*
    public void printFieldsRecursively(GameObject gameObject) {
        Field[] fields = gameObject.getClass().getDeclaredFields();
        if (fields.length != 0) {
            for (Field field : fields) {
                System.out.println(field.getName() + " " + field.getType().getSimpleName());
            }
        }
    }
    */
    
    public void printPrivateFields(Class c) {
        if (GameObject.class.isAssignableFrom(c)) {
            Field[] fields = c.getDeclaredFields();
            if (fields.length != 0) {
                for (Field field : fields) {
                    System.out.println(field.getName() + " " + field.getType().getSimpleName());
                }
            }
            printPrivateFields(c.getSuperclass());
        }
    }
    
    public void printClasses(Class c) {
        while (c != null) {
            System.out.println(c.getSimpleName());
            c = c.getSuperclass();
        }
    }
    
    public void printFieldsRecursively(Object object) {
        System.out.println(GameObject.class.isInstance(object));
        System.out.println(object.getClass().getGenericSuperclass().getTypeName());
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length != 0 && GameObject.class.isInstance(object)) {
            for (Field field : fields) {
                this.fieldsList.add(field);
            }
            printFieldsRecursively(object.getClass().getGenericSuperclass());
        }
    }
    
}
