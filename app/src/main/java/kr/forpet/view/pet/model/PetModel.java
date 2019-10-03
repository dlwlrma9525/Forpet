package kr.forpet.view.pet.model;

import android.content.Context;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.Vaccination;

public class PetModel {
    private AppDatabase mAppDatabase;

    public void loadAppDatabase(Context context) {
        mAppDatabase = SQLiteHelper.getAppDatabase(context);
    }

    public List<Vaccination> getVaccineListByPetType(String petType) {
        return mAppDatabase.vaccinationDAO().getByPetType(petType);
    }
}
