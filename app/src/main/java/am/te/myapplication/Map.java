package am.te.myapplication;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.view.View;
        import android.widget.Button;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    MarkerOptions marker = new MarkerOptions();
    GoogleMap map;
    private LatLng position = new LatLng(0, 0);
    boolean viewDeal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double longitude = extras.getDouble("longitude");
            double latitude = extras.getDouble("latitude");
            this.position = new LatLng(latitude, longitude);
            viewDeal = true;
            Button mapButton = (Button) findViewById(R.id.mapButton);
            mapButton.setText("Return");


        }

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Making the Marker
        marker.position(position);
        marker.title("Marker");
        marker.draggable(true);



    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setOnMarkerDragListener(this);
        map.addMarker(marker);

        if (viewDeal) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    position).zoom(12).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 7000,null);

        }

    }


    public void submitLocation(View view) {

        if (!viewDeal) {
            Intent location = new Intent();
            location.putExtra("lat", position.latitude);
            location.putExtra("lng", position.longitude);
            setResult(1,location);
        }
        finish();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker arg) {

    }

    @Override
    public void onMarkerDragEnd(Marker arg) {
        position = arg.getPosition();
    }

}