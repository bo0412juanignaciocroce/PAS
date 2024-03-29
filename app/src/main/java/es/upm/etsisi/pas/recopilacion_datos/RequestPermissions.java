package es.upm.etsisi.pas.recopilacion_datos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;

import es.upm.etsisi.pas.DebugTags;

public class RequestPermissions {
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String[] PERMISSIONS = {PERMISSION_CONTACTS,PERMISSION_LOCATION};
    public static final int PERMISSION_REQUEST_CODE = 0;
    public static ArrayList<String> permissions_missing = null;

    public static Boolean requestPermissions(Context context, Activity activity) {
        Boolean requested = true;
        permissions_missing = new ArrayList<>();
        for(String s : PERMISSIONS){
            if(PackageManager.PERMISSION_GRANTED != context.checkSelfPermission(s)) {
                Log.d(DebugTags.MANIFEST_PERMISSIONS,"Permiso falta: "+s);
                permissions_missing.add(s);
            }
        }
        if(permissions_missing.size()>0) {
            activity.requestPermissions(permissions_missing.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
        }else{
            requested = false;
        }
        Log.d(DebugTags.MANIFEST_PERMISSIONS,"Permiso pedidos!");
        return requested;
    }

    public static ArrayList<String> shouldAskPermissionsFailures(Activity activity){
        ArrayList<String> permissionNotAllowed = new ArrayList<>();
        for(String s:PERMISSIONS){
            if(!activity.shouldShowRequestPermissionRationale(s)){
                permissionNotAllowed.add(s);
                Log.d(DebugTags.MANIFEST_PERMISSIONS,"Missing permission: "+s);
            }
        }
        return permissionNotAllowed;
    }
}
