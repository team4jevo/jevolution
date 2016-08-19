package gamelogic;


import java.awt.Point;


class Creature extends GameObject {
    private boolean isAlive;
    
    Creature() {
        this.isAlive = true;
    }
    
    public void setStatus(boolean status) {
        this.isAlive = status;
    }
    
    public boolean getStatus() {
        return this.isAlive;
    }
    
    @Override
    public void setLocation (int x, int y) {
        this.location = new Point(x, y);
    }
    
    @Override
    public Point getLocation() {
        return this.location;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
}
