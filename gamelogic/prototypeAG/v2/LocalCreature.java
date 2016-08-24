package gamelogic2;


public class LocalCreature extends Organism {
    static final double SIGMA = 0.01;
    static final double K = 0.03162277660168379;
    
    public LocalCreature(String type, int x, int y) {
        super (type, x, y);
    }

}
