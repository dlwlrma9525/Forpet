package kr.forpet.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kr.forpet.data.dao.ForpetShopDAO;
import kr.forpet.data.entity.ForpetShop;

@Database(entities = {ForpetShop.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ForpetShopDAO forpetShopDAO();
}