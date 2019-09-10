package kr.forpet.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kr.forpet.data.dao.OpenTimeDAO;
import kr.forpet.data.dao.ShopDAO;
import kr.forpet.data.entity.OpenTime;
import kr.forpet.data.entity.Shop;

// https://developer.android.com/training/data-storage/room/index.html
// https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLngBounds
// https://tourspace.tistory.com/28
@Database(entities = {Shop.class, OpenTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopDAO shopDAO();

    public abstract OpenTimeDAO openTimeDAO();
}