package kr.forpet.view.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.map.GpsManager;
import kr.forpet.view.main.model.MainModel;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;
    private MainModel mMainModel;
    private GpsManager mGpsManager;

    public MainPresenterImpl() {
        mMainModel = new MainModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mMainModel.loadAppDatabase(context);
        mMainModel.initGooglePlayService(context);
        mGpsManager = GpsManager.getInstance(context);

        initSharedPreferences(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onMyLocate();
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
                    e1.setDistance(mGpsManager.getLocation().distanceTo(l1));
                    e2.setDistance(mGpsManager.getLocation().distanceTo(l2));

                    if (e1.getDistance() > e2.getDistance()) {
                        return 1;
                    } else if (e1.getDistance() < e2.getDistance()) {
                        return -1;
                    }

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
        mGpsManager.update((result) -> {
            if (result != null) {
                mView.updateMyLocate(new LatLng(result.getLatitude(), result.getLongitude()));
            } else {
                mView.updateMyLocate(new LatLng(mGpsManager.getLocation().getLatitude(), mGpsManager.getLocation().getLongitude()));
            }
        });
    }

    @Override
    public void onDrawer(Context context) {
        new AsyncTask<Void, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(Void... voids) {
                SharedPreferences sharedPref
                        = context.getSharedPreferences(context.getString(R.string.shared_name), Context.MODE_PRIVATE);
                String sharedData = sharedPref.getString(context.getString(R.string.shared_key_favorite), "");

                Gson gson = new Gson();
                String[] json = gson.fromJson(sharedData, String[].class);

                List<Shop> shopList = new ArrayList<>();
                for (String hash : json) {
                    shopList.add(mMainModel.getShop(hash));
                }

                return shopList;
            }

            @Override
            protected void onPostExecute(List<Shop> shopList) {
                super.onPostExecute(shopList);
                mView.updateFavorites(shopList);
            }
        }.execute();
    }

    private void initSharedPreferences(Context context) {
        SharedPreferences sharedPref
                = context.getSharedPreferences(context.getString(R.string.shared_name), Context.MODE_PRIVATE);
        String sharedData = sharedPref.getString(context.getString(R.string.shared_key_favorite), "");

        Gson gson = new Gson();
        String[] json = gson.fromJson(sharedData, String[].class);
        if (json == null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(context.getString(R.string.shared_key_favorite), gson.toJson(new String[]{}));
            editor.commit();
        }
    }
}
