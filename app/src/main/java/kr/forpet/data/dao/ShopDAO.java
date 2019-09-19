package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.Shop;

@Dao
public interface ShopDAO {

    @Insert
    void insert(Shop shop);

    @Update
    void update(Shop shop);

    @Query("SELECT * FROM forpet_shop")
    List<Shop> getAll();

    @Query("SELECT * From forpet_shop WHERE forpet_hash = :hashCode")
    Shop getByHashCode(String hashCode);

    @Query("SELECT * FROM forpet_shop WHERE place_name LIKE '%'||:name||'%'")
    List<Shop> getByName(String name);

    @Query("SELECT * FROM forpet_shop WHERE road_address_name LIKE '%'||:region||'%'")
    List<Shop> getByRegion(String region);

    /**
     * @param x1 southwest longitude
     * @param x2 northwest longitude
     * @param y1 southwest latitude
     * @param y2 northwest latitude
     * @return Shop entity
     */
    @Query("SELECT * FROM forpet_shop WHERE x BETWEEN :x1 AND :x2 AND y BETWEEN :y1 AND :y2 LIMIT 50")
    List<Shop> getByVisibleRegion(double x1, double x2, double y1, double y2);

    /**
     * @param x1      southwest longitude
     * @param x2      northwest longitude
     * @param y1      southwest latitude
     * @param y2      northwest latitude
     * @param catCode category_group_code
     * @return Shop entity
     */
    @Query("SELECT * FROM forpet_shop WHERE category_group_code = :catCode AND x BETWEEN :x1 AND :x2 AND y BETWEEN :y1 AND :y2 LIMIT 50")
    List<Shop> getByVisibleRegion(double x1, double x2, double y1, double y2, String catCode);
}