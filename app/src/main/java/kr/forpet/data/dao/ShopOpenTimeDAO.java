package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.ShopOpenTime;

@Dao
public interface ShopOpenTimeDAO {

    @Insert
    void insert(ShopOpenTime time);

    @Update
    void update(ShopOpenTime time);

    @Query("SELECT * FROM forpet_shop_open_time")
    List<ShopOpenTime> getAll();

    @Query("SELECT * From forpet_shop_open_time WHERE forpet_hash = :hashCode")
    List<ShopOpenTime> getByHashCode(String hashCode);
}