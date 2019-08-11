package kr.forpet.view.main.presenter;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface MainPresenter {
    void setView(View view);

    void onCreate(Context context);
    void onDestroy();

    void onMapReady(GoogleMap googleMap);

    interface View {
        void addMarker(MarkerOptions marker);
        void moveCamera(LatLng latLng);
    }
}