package kr.forpet.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kr.forpet.data.dao.ShopDAO;
import kr.forpet.data.dao.ShopOpenTimeDAO;
import kr.forpet.data.dao.ShopPharmacyDAO;
import kr.forpet.data.entity.ShopOpenTime;
import kr.forpet.data.entity.ShopPharmacy;
import kr.forpet.data.entity.Shop;

// https://developer.android.com/training/data-storage/room/index.html
// https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLngBounds
// https://tourspace.tistory.com/28
@Database(entities = {Shop.class, ShopOpenTime.class, ShopPharmacy.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ShopDAO shopDAO();
    public abstract ShopOpenTimeDAO shopOpenTimeDAO();
    public abstract ShopPharmacyDAO shopPharmacyDAO();
}