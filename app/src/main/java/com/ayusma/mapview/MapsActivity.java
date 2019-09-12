package com.ayusma.mapview;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    public static GoogleMap mMap;
    private LinearLayoutManager lLayout;
    MarkerOptions marker;
    private Marker locationmarker;
    public static List<Marker> markerslist = new ArrayList<Marker>();
   private RecyclerViewAdapter rcAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


    }

    List<ItemObject> allItems = new ArrayList<ItemObject>();
    private List<ItemObject> getAllItemList(){

        return allItems;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);


        double latitude[] = {13.07891, 13.11602, 13.11355, 13.12511, 13.08367};
        double longitude[] = {80.28215, 80.23166, 80.29613, 80.29554, 80.23961};

        // lets place some 5 markers
        for (int i = 0; i < 5; i++) {
            double[] randomLocation = createRandLocation(latitude[i],
                    longitude[i]);
            // Adding a icon_marker
           marker = new MarkerOptions().position(
                    new LatLng(latitude[i], longitude[i]))
                    .title("Map " + i);

           LatLng latlng = marker.getPosition();
           String position = String.valueOf(latlng.latitude+ ","+ latlng.longitude);
           String address = getAddress(latlng.latitude,latlng.longitude);
            allItems.add(new ItemObject("Map " + i,address ,"LatLng: "+position));
             locationmarker = mMap.addMarker(marker);
             markerslist.add(i,locationmarker);

            // Move the camera to last position with a zoom level
            if (i == 4) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(randomLocation[0],
                                randomLocation[1])).zoom(15).build();

                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));



            }

    }


        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(MapsActivity.this,LinearLayoutManager.HORIZONTAL,false);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);



        rcAdapter = new RecyclerViewAdapter(MapsActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
}


    private double[] createRandLocation(double latitude, double longitude) {

        return new double[] {
                latitude + ((Math.random() - 0.5) / 500),longitude + ((Math.random() - 0.5) / 500),150 + ((Math.random() - 0.5) * 10) };
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().equals("Map 0")){
            lLayout.scrollToPosition(0);
        }
        if (marker.getTitle().equals("Map 1")){
            lLayout.scrollToPosition(1);
        }
        if (marker.getTitle().equals("Map 2")){
            lLayout.scrollToPosition(2);
        }
        if (marker.getTitle().equals("Map 3")){
            lLayout.scrollToPosition(3);
        }
        if (marker.getTitle().equals("Map 4")){
            lLayout.scrollToPosition(4);
        }

        return false;
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append(",");
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapview_menu, menu);
        super.onCreateOptionsMenu(menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rcAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
