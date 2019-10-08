package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.Emergency;

@Dao
public interface EmergencyDAO {

    @Insert
    void insert(Emergency emergency);

    @Update
    void update(Emergency emergency);

    @Query("SELECT * FROM forpet_emergency")
    List<Emergency> getAll();
}