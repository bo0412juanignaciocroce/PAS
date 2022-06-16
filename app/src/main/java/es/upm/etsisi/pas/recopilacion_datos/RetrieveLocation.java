package es.upm.etsisi.pas.recopilacion_datos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import es.upm.etsisi.pas.DebugTags;

public class RetrieveLocation extends Activity {

    private final LocationManager mgr;
    private final FirebaseLocation firebaselocation;

    public RetrieveLocation(LocationManager mgr) {
        this.mgr = mgr;
        firebaselocation = new FirebaseLocation();
    }

    public void uploadLocation(LocationEntity location){
        firebaselocation.UploadLocation(location);
    }

    public LocationEntity getLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = mgr.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location =
                mgr.getLastKnownLocation(bestProvider);
        double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            Log.d(DebugTags.FIREBASE_STORAGE,"Location");
            return new LocationEntity(lat, lon);
        }
        catch (NullPointerException e){
            Log.d(DebugTags.FIREBASE_STORAGE,"No location");
            e.printStackTrace();
            return null;
        }
    }
}
