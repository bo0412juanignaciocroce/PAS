package es.upm.etsisi.pas.recopilacion_datos;

import com.google.gson.Gson;

public class LocationEntity {
    static Gson gson = new Gson();
    private double latitude;
    private double longitude;

    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String serializeGSon(){
        return gson.toJson(this);
    }

}
