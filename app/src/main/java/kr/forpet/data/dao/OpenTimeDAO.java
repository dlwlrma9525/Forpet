package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.OpenTime;

@Dao
public interface OpenTimeDAO {

    @Insert
    void insert(OpenTime openTime);

    @Update
    void update(OpenTime openTime);

    @Query("SELECT * FROM forpet_shop_open_time")
    List<OpenTime> getAll();

    @Query("SELECT * From forpet_shop_open_time WHERE forpet_hash = :hashCode")
    List<OpenTime> getByHashCode(String hashCode);
}