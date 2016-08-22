package gamelogic;


import java.awt.Point;


class Creature extends GameObject {
    private boolean isAlive;
    private int cooperativeness;
    
    Creature() {
        this.isAlive = true;
    }
    
    public void setStatus(boolean status) {
        this.isAlive = status;
        this.cooperativeness = 0;
    }
    
    public boolean getStatus() {
        return this.isAlive;
    }
    
    public void setCooperativeness(int value) {
        this.cooperativeness = value;
    }
    
    public int getCooperativeness() {
        return this.cooperativeness;
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
