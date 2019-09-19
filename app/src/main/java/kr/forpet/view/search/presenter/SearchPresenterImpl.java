package kr.forpet.view.search.presenter;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import kr.forpet.data.entity.Shop;
import kr.forpet.map.GpsManager;
import kr.forpet.view.search.model.SearchModel;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchPresenter.View mView;
    private SearchModel mSearchModel;
    private GpsManager mGpsManager;

    public SearchPresenterImpl() {
        mSearchModel = new SearchModel();
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mSearchModel.loadAppDatabase(context);
        mGpsManager = GpsManager.getInstance(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSearchByName(String keyword) {
        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                List<Shop> shopList = mSearchModel.getShopListByName(strings[0]);
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
                mView.updateResult(shopList);
            }
        }.execute(keyword);
    }

    @Override
    public void onSearchByRegion(String keyword) {
        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                List<Shop> shopList = mSearchModel.getShopListByRegion(strings[0]);
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
                mView.updateResult(shopList);
            }
        }.execute(keyword);
    }
}
