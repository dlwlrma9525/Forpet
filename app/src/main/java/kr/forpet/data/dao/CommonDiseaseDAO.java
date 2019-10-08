package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.CommonDisease;

@Dao
public interface CommonDiseaseDAO {

    @Insert
    void insert(CommonDisease commonDisease);

    @Update
    void update(CommonDisease commonDisease);

    @Query("SELECT * FROM forpet_common_disease")
    List<CommonDisease> getAll();
}