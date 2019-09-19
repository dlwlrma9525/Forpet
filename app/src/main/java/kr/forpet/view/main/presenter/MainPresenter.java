package kr.forpet.view.main.presenter;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import kr.forpet.data.entity.Shop;

public interface MainPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();
    void onMapReady(GoogleMap googleMap);

    void onMapUpdate(LatLngBounds latLngBounds, String categoryGroupCode);
    void onMyLocate();
    void onDrawer(Context context);

    interface View {

        void updateMyLocate(LatLng latLng);
        void updateMap(List<Shop> shopList);
        void updateFavorites(List<Shop> shopList);
    }
}