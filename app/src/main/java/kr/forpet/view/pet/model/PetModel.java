package kr.forpet.view.pet.model;

import android.content.Context;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.CommonDisease;
import kr.forpet.data.entity.Emergency;
import kr.forpet.data.entity.Food;
import kr.forpet.data.entity.Vaccination;

public class PetModel {
    private AppDatabase mAppDatabase;

    public void loadAppDatabase(Context context) {
        mAppDatabase = SQLiteHelper.getAppDatabase(context);
    }

    public List<Food> getFoodListByPetType(String petType) {
        return mAppDatabase.foodDAO().getByPetType(petType);
    }

    public List<Vaccination> getVaccineListByPetType(String petType) {
        return mAppDatabase.vaccinationDAO().getByPetType(petType);
    }

    public List<Emergency> getEmergencyList() {
        return mAppDatabase.emergencyDAO().getAll();
    }

    public List<CommonDisease> getCommonDiseaseList() {
        return mAppDatabase.commonDiseaseDAO().getAll();
    }
}
