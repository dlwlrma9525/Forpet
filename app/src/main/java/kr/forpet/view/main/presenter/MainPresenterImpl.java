package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

import java.util.Collections;
import java.util.List;

import kr.forpet.data.entity.Shop;
import kr.forpet.view.main.model.MainModel;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;
    private MainModel mMainModel;
    private Location mLastLocation;

    public MainPresenterImpl() {
        mMainModel = new MainModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mMainModel.initAppDatabase(context);
        mMainModel.initGooglePlayService(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onMapUpdate(LatLngBounds latLngBounds, String categoryGroupCode) {
        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                List<Shop> shopList = mMainModel.getShopList(latLngBounds, strings[0]);

                Collections.sort(shopList, (e1, e2) -> {
                    Location l1 = new Location(e1.getForpetHash());
                    l1.setLatitude(e1.getY());
                    l1.setLongitude(e1.getX());

                    Location l2 = new Location(e2.getForpetHash());
                    l2.setLatitude(e2.getY());
                    l2.setLongitude(e2.getX());

                    // distanceTo return meter..
                    e1.setDistance(mLastLocation.distanceTo(l1));
                    e2.setDistance(mLastLocation.distanceTo(l2));

                    if (e1.getDistance() > e2.getDistance())
                        return 1;
                    else if (e1.getDistance() < e2.getDistance())
                        return -1;

                    return 0;
                });

                return shopList;
            }

            @Override
            protected void onPostExecute(List<Shop> shopList) {
                super.onPostExecute(shopList);
                mView.updateMap(shopList);
            }
        }.execute(categoryGroupCode);
    }

    @Override
    public void onMyLocate() {
        mMainModel.getMyLocation().addOnCompleteListener((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                mLastLocation = task.getResult();
                mView.updateMyLocate(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        });
    }
}
