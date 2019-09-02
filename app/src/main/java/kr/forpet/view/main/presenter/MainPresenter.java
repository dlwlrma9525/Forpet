package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

import kr.forpet.data.entity.Shop;

public interface MainPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onMapReady(GoogleMap googleMap);
    void onMarkerClick(Marker marker);
    void onSearch(Shop.CatCode catCode, LatLngBounds latLngBounds);
    void onMyLocate(OnCompleteListener<Location> listener);

    interface View {

        Context getContext();

        void addMarker(MarkerOptions markerOptions);
        void moveCamera(LatLng latLng);
        void showPopup(List<Shop> list);
    }
}