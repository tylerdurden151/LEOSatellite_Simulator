package SatelliteSim;
// Class to provide handling of satellite data
// PLACE holder

public class SatelliteData {
    private String name;
    private int mass;
    private int altitude;
    private int area;
    private int longitude;
    private int latitude;
    private int speed;

    // add other variables as needed

    public SatelliteData (String name, int mass, int area, int altitude) {

        this.name = name;
        this.mass = mass;
        this.altitude = altitude;
        this. area = area;

    }



    public int getMass() {
        return mass;
    }
    public int getAltitude (){
        return altitude;
    }
    public int getArea (){
        return area;
    }
    public String getName() {
        return name;
    }
}



