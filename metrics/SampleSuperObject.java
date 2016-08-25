package metrics;


import jevosim.GameObject;


public class SampleSuperObject extends GameObject {
    public SampleSuperObject() throws IllegalArgumentException, IllegalAccessException {
        super();
    }
    private String superString;
    private int superInt;
    private boolean superBoolean;
    private int[] superArrayInt;
    private String[][] superString2DArray;
    
    public String getSuperString() {
        return superString;
    }
    public void setSuperString(String superString) {
        this.superString = superString;
    }
    public int getSuperInt() {
        return superInt;
    }
    public void setSuperInt(int superInt) {
        this.superInt = superInt;
    }
    public boolean isSuperBoolean() {
        return superBoolean;
    }
    public void setSuperBoolean(boolean superBoolean) {
        this.superBoolean = superBoolean;
    }
    public int[] getSuperArrayInt() {
        return superArrayInt;
    }
    public void setSuperArrayInt(int[] superArrayInt) {
        this.superArrayInt = superArrayInt;
    }
    public String[][] getSuperString2DArray() {
        return superString2DArray;
    }
    public void setSuperString2DArray(String[][] superString2DArray) {
        this.superString2DArray = superString2DArray;
    }
}
