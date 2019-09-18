package kr.forpet.view.search.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import kr.forpet.data.entity.Shop;
import kr.forpet.view.search.model.SearchModel;

public class SearchPresenterImpl implements SearchPresenter {
    private View mView;
    private SearchModel mSearchModel;

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
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSearchByName(String keyword, String categoryGroupCode) {
        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                return mSearchModel.getShopListByName(strings[0], strings[1]);
            }

            @Override
            protected void onPostExecute(List<Shop> shopList) {
                super.onPostExecute(shopList);
                mView.updateResult(shopList);
            }
        }.execute(keyword, categoryGroupCode);
    }

    @Override
    public void onSearchByRegion(String keyword, String categoryGroupCode) {
        new AsyncTask<String, Void, List<Shop>>() {
            @Override
            protected List<Shop> doInBackground(String... strings) {
                return mSearchModel.getShopListByRegion(strings[0], strings[1]);
            }

            @Override
            protected void onPostExecute(List<Shop> shopList) {
                super.onPostExecute(shopList);
                mView.updateResult(shopList);
            }
        }.execute(keyword, categoryGroupCode);
    }
}
