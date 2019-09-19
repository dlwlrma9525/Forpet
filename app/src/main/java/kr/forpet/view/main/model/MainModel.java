package kr.forpet.view.main.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.Shop;

public class MainModel {

    private AppDatabase mAppDatabase;

    public void initGooglePlayService(Context context) {

    }

    public void loadAppDatabase(Context context) {
        mAppDatabase = SQLiteHelper.getAppDatabase(context);
    }

    public Shop getShop(String hashCode) {
        return mAppDatabase.shopDAO().getByHashCode(hashCode);
    }

    public List<Shop> getShopList(LatLngBounds bounds, String categoryGroupCode) {
        List<Shop> shopList = mAppDatabase.shopDAO().getByVisibleRegion(
                bounds.southwest.longitude,
                bounds.northeast.longitude,
                bounds.southwest.latitude,
                bounds.northeast.latitude,
                categoryGroupCode
        );

        for (Shop shop : shopList) {
            if (shop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString()))
                shop.setShopPharmacy(mAppDatabase.shopPharmacyDAO().getByHashCode(shop.getForpetHash()));

            shop.setShopOpenTimeList(mAppDatabase.shopOpenTimeDAO().getByHashCode(shop.getForpetHash()));
        }

        return shopList;
    }
}