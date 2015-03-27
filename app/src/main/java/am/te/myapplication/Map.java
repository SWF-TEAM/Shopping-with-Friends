package am.te.myapplication;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.view.View;
        import android.widget.Toast;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    MarkerOptions marker = new MarkerOptions();
    GoogleMap map;
    private LatLng position = new LatLng(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double longitude = extras.getDouble("longitude");
            double latitude = extras.getDouble("latitude");
            this.position = new LatLng(latitude, longitude);
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

    }


    public void submitLocation(View view) {
        Intent location = new Intent();
        location.putExtra("lat", position.latitude);
        location.putExtra("lng", position.longitude);
        setResult(1,location);
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