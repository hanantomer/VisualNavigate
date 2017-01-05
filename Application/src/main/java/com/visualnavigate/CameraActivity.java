
package com.visualnavigate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.camera_container, CameraFragment.newInstance())
                    .commit();
        }

        Intent intent = new Intent(this, GetLocationService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int size = mOptionsMenu.size();
        for (int i = 0; i < size; i++) {
            mOptionsMenu.getItem(i).setChecked(false);
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_mode_recording) {
            item.setChecked(true);
            Global.operationMode = Global.OperationMode.Recording;
        }
        else if (id == R.id.action_mode_navigating) {
            item.setChecked(true);
            Global.operationMode = Global.OperationMode.Navigating;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng current = new LatLng(Global.lastLatitude,Global.lastLongitude);

        googleMap.addMarker(new MarkerOptions().position(current)
                .title("Here"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }
}
