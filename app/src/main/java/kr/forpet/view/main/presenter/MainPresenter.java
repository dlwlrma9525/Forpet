package kr.forpet.view.main.presenter;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.data.entity.ForpetShop;

public interface MainPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onMapReady(GoogleMap googleMap);
    void onMapSearch(ForpetShop.CatCode catCode, LatLngBounds latLngBounds);
    void onMarkerClick(Marker marker);
    void onRequestGps();

    interface View {

        Context getContext();

        void addMarker(MarkerOptions markerOptions);
        void moveCamera(LatLng latLng);

        void showCard(ForpetShop shop);
    }
}