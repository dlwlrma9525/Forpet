package kr.forpet.view.search.model;

import android.content.Context;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.Shop;

public class SearchModel {
    private AppDatabase mAppDatabase;

    public void loadAppDatabase(Context context) {
        mAppDatabase = SQLiteHelper.getAppDatabase(context);
    }

    public List<Shop> getShopListByName(String name) {
        return mAppDatabase.shopDAO().getByName(name);
    }

    public List<Shop> getShopListByRegion(String region) {
        return mAppDatabase.shopDAO().getByRegion(region);
    }
}
