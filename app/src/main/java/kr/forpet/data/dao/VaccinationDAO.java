package kr.forpet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.forpet.data.entity.Vaccination;

@Dao
public interface VaccinationDAO {

    @Insert
    void insert(Vaccination vaccination);

    @Update
    void update(Vaccination vaccination);

    @Query("SELECT * FROM forpet_vaccination")
    List<Vaccination> getAll();

    @Query("SELECT * From forpet_vaccination WHERE pet_type = :petType")
    List<Vaccination> getByPetType(String petType);
}