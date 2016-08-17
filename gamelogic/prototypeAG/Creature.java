package jevolution;


import java.awt.Point;


class Creature extends GameObject {
    private boolean isAlive;
    
    Creature() {
        this.isAlive = true;
    }
    
    void setStatus(boolean status) {
        this.isAlive = status;
    }
    
    boolean getStatus() {
        return this.isAlive;
    }
    
    @Override
    void setLocation (int x, int y) {
        this.location = new Point(x, y);
    }
    
    @Override
    Point getLocation() {
        return this.location;
    }
    
    @Override
    String getType() {
        return this.getClass().getSimpleName();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
    
}
