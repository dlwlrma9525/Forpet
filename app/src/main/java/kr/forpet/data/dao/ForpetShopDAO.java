package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.ForpetShop;

// https://developer.android.com/training/data-storage/room/index.html
// https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLngBounds
// https://tourspace.tistory.com/28
@Dao
public interface ForpetShopDAO {

    @Insert // @Insert(onConflict = OnConflictStrategy.REPLACE) PK
    void insert(ForpetShop shop);

    @Update
    void update(ForpetShop shop);

    @Query("SELECT * FROM forpet_shop")
    List<ForpetShop> getAll();

    @Query("SELECT * From forpet_shop WHERE place_name = :name")
    ForpetShop getByShopName(String name);

    /**
     * @param x1 southwest longitude
     * @param x2 northwest longitude
     * @param y1 southwest latitude
     * @param y2 northwest latitude
     * @return ForpetShop entity
     */
    @Query("SELECT * FROM forpet_shop WHERE x BETWEEN :x1 AND :x2 AND y BETWEEN :y1 AND :y2 LIMIT 50")
    List<ForpetShop> getByVisibleRegion(double x1, double x2, double y1, double y2);

    /**
     * @param x1 southwest longitude
     * @param x2 northwest longitude
     * @param y1 southwest latitude
     * @param y2 northwest latitude
     * @param catCode category_group_code
     * @return ForpetShop entity
     */
    @Query("SELECT * FROM forpet_shop WHERE category_group_code = :catCode AND x BETWEEN :x1 AND :x2 AND y BETWEEN :y1 AND :y2 LIMIT 50")
    List<ForpetShop> getByVisibleRegion(double x1, double x2, double y1, double y2, String catCode);
}