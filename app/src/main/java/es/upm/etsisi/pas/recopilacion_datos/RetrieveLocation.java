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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class RetrieveLocation extends Activity {

    public static double myLocationLatitude;
    public static double myLocationLongitude;
    private LocationManager mgr;
    private Context context;
    private Activity activity;
    private FirebaseLocation firebaselocation;

    public RetrieveLocation(Context context, Activity activity, LocationManager mgr) {
        this.mgr = mgr;
        this.context=context;
        this.activity=activity;
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
