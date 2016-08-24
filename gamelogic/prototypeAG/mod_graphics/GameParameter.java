package jevo;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class GameParameter {

    //do not mind this class for now
    public String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String value;

    public GameParameter(String property, String value){
        this.property = property;
        this.value = value;

    }




}
