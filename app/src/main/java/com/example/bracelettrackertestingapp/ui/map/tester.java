package com.example.bracelettrackertestingapp.ui.map;
import android.content.Context;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bracelettrackertestingapp.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.util.Timer;
import java.util.TimerTask;

public class tester extends AppCompatActivity {
    MapView map;
    Marker my_marker ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("rinel tester","inside tester");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = (MapView) findViewById(R.id.map);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short) 12);
        Configuration.getInstance().setCacheMapTileOvershoot((short) 12);
        // Create a custom tile source
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 512, ".png",
                new String[]{"https://a.tile.openstreetmap.org/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });

        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        GeoPoint startPoint;
        startPoint = new GeoPoint(-25.7566505, +28.2783488);
        mapController.setZoom(20.0);
        mapController.setCenter(startPoint);
        final Context context = this;
        map.invalidate();
        my_marker =  new Marker(map);
        createmarker(my_marker,-25.7566505, +28.2783488,"bill");
        Log.i("rinel map","bill created");
        new Timer().schedule(
                new TimerTask(){

                    @Override
                    public void run(){
                        moveMarker(my_marker,-25.7564505, +28.2784488,"rinel");
                        Log.i("rinel map","rinel created");
                        //if you need some code to run when the delay expires
                    }

                }, 3000);
        Log.i("rinel map","rinel created");
    }

    public void createmarker(Marker marker,double lat,double longt,String name) {
        if (map == null) {
            return;
        }


        marker.setPosition(new GeoPoint(lat, longt));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(name);
        marker.setPanToView(true);
        map.getOverlays().add(marker);
        map.invalidate();
    }
    public void moveMarker(Marker marker,double lat,double longt,String name) {
        if (map == null) {
            return;
        }


        marker.setPosition(new GeoPoint(lat, longt));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(name);
        marker.setPanToView(true);
        map.invalidate();
    }
}
