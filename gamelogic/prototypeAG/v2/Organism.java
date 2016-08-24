package gamelogic2;

import graphics.GameObject;

public class Organism extends GameObject {
    private boolean isAlive;
    
    public Organism(String type, int x, int y) {
        super(type, x, y);
    }
    
    public void setState(boolean state) {
        this.isAlive = state;
    }

    public boolean getState() {
        return this.isAlive;
    }

}
