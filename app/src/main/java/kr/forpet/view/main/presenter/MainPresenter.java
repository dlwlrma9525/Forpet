package kr.forpet.view.main.presenter;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.data.entity.ForpetShop;

public interface MainPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onMapReady(GoogleMap googleMap);
    void onMapSearch(ForpetShop.CatCode catCode, LatLngBounds latLngBounds);
    void onMyGps();

    interface View {

        Context getContext();

        void addMarker(MarkerOptions marker);
        void moveCamera(LatLng latLng);
        void clearMap();
    }
}