package es.upm.etsisi.pas.recopilacion_datos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class RetrieveLocation extends Activity {

    public static double myLocationLatitude;
    public static double myLocationLongitude;
    private static final int REQUEST_LOCATION = 1;

    public RetrieveLocation(Context context, Activity activity, LocationManager mgr) {
        LocationEntity locationEntity;
        requestPermission(context, activity);
        locationEntity = getLocation(context, activity, mgr);
        FirebaseLocation firebaselocation = new FirebaseLocation();
        firebaselocation.UploadLocation(locationEntity);
    }

    public LocationEntity getLocation(Context context, Activity activity, LocationManager mgr) {
        Criteria criteria = new Criteria();
        String bestProvider = mgr.getBestProvider(criteria, false);
        requestPermission(context, activity);
        @SuppressLint("MissingPermission") Location location = mgr.getLastKnownLocation(bestProvider);
        Double lat,lon;
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

    private void requestPermission(Context context, Activity activity) {
        while(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

}
