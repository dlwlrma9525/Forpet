package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

import kr.forpet.data.entity.Shop;

public interface MainPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onMapReady(GoogleMap googleMap);
    void onMapUpdate(LatLngBounds latLngBounds, MenuItem item);
    void onMarkerClick();
    void onMyLocate(OnCompleteListener<Location> listener);

    interface View {

        void updatePopup(List<Shop> shopList);
        void updateMap(List<MarkerOptions> markerOptionsList);
        void clearMap();
    }
}