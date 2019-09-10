package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.OpenTime;
import kr.forpet.data.entity.Pharmacy;

@Dao
public interface PharmacyDAO {

    @Insert
    void insert(Pharmacy pharmacy);

    @Update
    void update(Pharmacy pharmacy);

    @Query("SELECT * FROM forpet_shop_pharmacy")
    List<Pharmacy> getAll();

    @Query("SELECT * From forpet_shop_pharmacy WHERE forpet_hash = :hashCode")
    Pharmacy getByHashCode(String hashCode);
}