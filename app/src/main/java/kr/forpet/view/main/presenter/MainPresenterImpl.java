package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.map.MarkerBuilder;
import kr.forpet.view.main.model.MainModel;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;
    private MainModel mMainModel;

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
    public void onMapUpdate(LatLngBounds latLngBounds, MenuItem item) {
        Shop.CategoryGroupCode code = null;
        switch (item.getItemId()) {
            case R.id.action_supplies:
                code = Shop.CategoryGroupCode.SHOP;
                break;
            case R.id.action_pharm:
                code = Shop.CategoryGroupCode.PHARM;
                break;
            case R.id.action_hospital:
                code = Shop.CategoryGroupCode.HOSPITAL;
                break;
            case R.id.action_hair:
                code = Shop.CategoryGroupCode.BEAUTY;
                break;
        }

        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                return mMainModel.getShopList(latLngBounds, strings[0]);
            }

            @Override
            protected void onPostExecute(List<Shop> shops) {
                super.onPostExecute(shops);

                Collections.sort(shops, (e1, e2) -> {
                    Location ct = new Location("");
                    ct.setLatitude(latLngBounds.getCenter().latitude);
                    ct.setLongitude(latLngBounds.getCenter().longitude);

                    Location l1 = new Location(e1.getForpetHash());
                    l1.setLatitude(e1.getY());
                    l1.setLongitude(e1.getX());

                    Location l2 = new Location(e2.getForpetHash());
                    l2.setLatitude(e2.getY());
                    l2.setLongitude(e2.getX());

                    if (ct.distanceTo(l1) > ct.distanceTo(l2))
                        return 1;
                    else if (ct.distanceTo(l1) < ct.distanceTo(l2))
                        return -1;

                    return 0;
                });

                List<MarkerOptions> markerOptionsList = new ArrayList<>();
                for (Shop shop : shops) {
                    markerOptionsList.add(new MarkerBuilder(new LatLng(shop.getY(), shop.getX()))
                            .categoryGroupCode(shop.getCategoryGroupCode())
                            .placeName(shop.getPlaceName())
                            .forpetHash(shop.getForpetHash())
                            .build());
                }

                mView.clearMap();
                mView.updateMap(markerOptionsList);
            }
        }.execute(code.toString());
    }

    @Override
    public void onMyLocate(OnCompleteListener<Location> listener) {
        Task task = mMainModel.getMyLocation();
        task.addOnCompleteListener(listener);
    }
}
