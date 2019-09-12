package com.ayusma.mapview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;

import static com.ayusma.mapview.MapsActivity.mMap;
import static com.ayusma.mapview.MapsActivity.markerslist;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView markerName;
    public TextView locationname;
    public TextView position;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        markerName = itemView.findViewById(R.id.markerName);
        locationname = itemView.findViewById(R.id.location_name);
        position = itemView.findViewById(R.id.position);
    }

    @Override
    public void onClick(View view) {
        int position = getPosition();
       Marker marker = markerslist.get(position);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition()).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
       marker.showInfoWindow();

    }
}