package jevolution;

import java.awt.Point;


class Food extends GameObject {

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
        
    }

}
