package gamelogic;


class LocalCreature extends LocalGameObject {
    static final double SIGMA = 0.01;
    static final double K = 0.03162277660168379;
    private boolean isAlive;
    private int xCoord;
    private int yCoord;

    LocalCreature() {
        this.isAlive = true;
    }

    public void setStatus(boolean status) {
        this.isAlive = status;
    }

    public boolean getStatus() {
        return this.isAlive;
    }

    @Override
    public void setXCoord(int x) {
        this.xCoord = x;
    }
    
    @Override
    public void setYCoord(int y) {
        this.xCoord = y;
    }

    @Override
    public int getXCoord() {
        return this.xCoord;
    }
    
    @Override
    public int getYCoord() {
        return this.yCoord;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

}
