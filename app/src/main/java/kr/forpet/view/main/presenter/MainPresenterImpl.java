package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import kr.forpet.data.entity.ForpetShop;
import kr.forpet.map.MarkerBuilder;
import kr.forpet.view.main.model.MainModel;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;
    private MainModel mainModel;

    public MainPresenterImpl() {
        mainModel = new MainModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mainModel.initAppDatabase(context);
        mainModel.initGooglePlayService(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onRequestGps();
    }

    @Override
    public void onMapSearch(ForpetShop.CatCode catCode, LatLngBounds latLngBounds) {
        new AsyncTask<String, Void, List<ForpetShop>>() {
            @Override
            protected List<ForpetShop> doInBackground(String... strings) {
                return mainModel.getForpetShopList(latLngBounds, strings[0]);
            }

            @Override
            protected void onPostExecute(List<ForpetShop> shops) {
                super.onPostExecute(shops);

                for (ForpetShop shop : shops) {
                    mView.addMarker(new MarkerBuilder(mView.getContext(), new LatLng(shop.getY(), shop.getX()))
                            .catCode(shop.getCategoryGroupCode())
                            .hash(shop.getForpetHash())
                            .title(shop.getPlaceName())
                            .event(shop.getEvent())
                            .build());
                }
            }
        }.execute(catCode.toString());
    }

    @Override
    public void onMarkerClick(Marker marker) {
        new AsyncTask<String, Void, ForpetShop>() {
            @Override
            protected ForpetShop doInBackground(String... strings) {
                return mainModel.getForpetShop(strings[0]);
            }

            @Override
            protected void onPostExecute(ForpetShop shop) {
                super.onPostExecute(shop);
                mView.showCard(shop);
            }
        }.execute(marker.getSnippet());
    }

    @Override
    public void onRequestGps() {
        try {
            Task task = mainModel.getMyLocation();
            task.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location result = task.getResult();
                        LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.snippet("Me");

                        mView.addMarker(markerOptions);
                        mView.moveCamera(latLng);
                    } else {
                        Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                        Log.e("GooglePlayServices", "Exception: %s", task.getException());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
