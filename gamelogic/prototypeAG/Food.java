package gamelogic;

import java.awt.Point;


class Food extends GameObject {

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

    /**
     * @param args
     */
    public static void main(String[] args) {
        
    }

}
