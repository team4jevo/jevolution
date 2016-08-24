package metrics;


public class GameObjectRecord {
    private static long n = 0;
    private long id;
    private graphics.GameObject gameObject;
    private String type;
    
    public GameObjectRecord(graphics.GameObject gameObject) {
        this.gameObject = gameObject;
        this.id = ++GameObjectRecord.n;
        this.type = gameObject.getClass().getSimpleName();
    }
    
    public GameObjectRecord(graphics.GameObject gameObject, String type) {
        this.gameObject = gameObject;
        this.id = ++GameObjectRecord.n;
        this.type = type;
    }
    
    public long getId() {
        return this.id;
    }
    
    public graphics.GameObject getGameObject() {
        return this.gameObject;
    }
    
    public String getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.id + " " + this.type;
    }
   
}
