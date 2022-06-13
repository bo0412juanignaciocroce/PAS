package es.upm.etsisi.pas.recopilacion_datos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class RetrieveLocation extends Activity {

    private final LocationManager mgr;
    private final Context context;
    private final FirebaseLocation firebaselocation;

    public RetrieveLocation(Context context, LocationManager mgr) {
        this.mgr = mgr;
        this.context=context;
        firebaselocation = new FirebaseLocation();
    }

    public void uploadLocation(LocationEntity location){
        firebaselocation.UploadLocation(location, context);
    }

    public LocationEntity getLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = mgr.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = mgr.getLastKnownLocation(bestProvider);
        double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            return new LocationEntity(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
