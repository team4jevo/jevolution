package jevo;

/**
 * Created by LongJohn on 8/23/2016.
 */
public class tmpTableItem {
    private String someProperty = "";

    public int getSomeOtherProperty() {
        return someOtherProperty;
    }

    public void setSomeOtherProperty(int someOtherProperty) {
        this.someOtherProperty = someOtherProperty;
    }

    private int someOtherProperty = 9;

    public tmpTableItem(){
        this.someOtherProperty  = 5;
        this.someProperty = "fdfdfd";
    }

    public tmpTableItem(String s, int i){
        this.someProperty = s;
        this.someOtherProperty = i;

    }

    public String getSomeProperty() {
        return someProperty;
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty = someProperty;
    }
}
