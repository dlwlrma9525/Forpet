package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.ShopPharmacy;

@Dao
public interface ShopPharmacyDAO {

    @Insert
    void insert(ShopPharmacy shopPharmacy);

    @Update
    void update(ShopPharmacy shopPharmacy);

    @Query("SELECT * FROM forpet_shop_pharmacy")
    List<ShopPharmacy> getAll();

    @Query("SELECT * From forpet_shop_pharmacy WHERE forpet_hash = :hashCode")
    ShopPharmacy getByHashCode(String hashCode);
}