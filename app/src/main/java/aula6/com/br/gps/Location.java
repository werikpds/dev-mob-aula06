package aula6.com.br.gps;


import java.io.Serializable;

public class Location implements Serializable {

    private int id;
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Location(int id, double latitude, double longitude) {
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Location(){

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Lat: " + latitude + ", Long: " + longitude;
    }
}
