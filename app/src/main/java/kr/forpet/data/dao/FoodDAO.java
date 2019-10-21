package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.Food;

@Dao
public interface FoodDAO {

    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Query("SELECT * FROM forpet_food")
    List<Food> getAll();

    @Query("SELECT * From forpet_food WHERE pet_type = :petType AND food_type = :foodType")
    List<Food> getByType(String petType, String foodType);

    @Query("SELECT * From forpet_food WHERE pet_type = :petType")
    List<Food> getByPetType(String petType);

    @Query("SELECT * From forpet_food WHERE food_type = :foodType")
    List<Food> getByFoodType(String foodType);
}