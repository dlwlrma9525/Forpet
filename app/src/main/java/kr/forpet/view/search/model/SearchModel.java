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
        List<Shop> shopList = mAppDatabase.shopDAO().getByName(name);

        for (Shop shop : shopList) {
            if (shop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString()))
                shop.setShopPharmacy(mAppDatabase.shopPharmacyDAO().getByHashCode(shop.getForpetHash()));

            shop.setShopOpenTimeList(mAppDatabase.shopOpenTimeDAO().getByHashCode(shop.getForpetHash()));
        }

        return shopList;
    }

    public List<Shop> getShopListByRegion(String region) {
        List<Shop> shopList = mAppDatabase.shopDAO().getByRegion(region);

        for (Shop shop : shopList) {
            if (shop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString()))
                shop.setShopPharmacy(mAppDatabase.shopPharmacyDAO().getByHashCode(shop.getForpetHash()));

            shop.setShopOpenTimeList(mAppDatabase.shopOpenTimeDAO().getByHashCode(shop.getForpetHash()));
        }

        return shopList;
    }
}
