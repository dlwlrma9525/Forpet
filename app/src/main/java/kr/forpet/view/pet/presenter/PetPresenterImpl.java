package kr.forpet.view.pet.presenter;

import android.content.Context;
import android.os.AsyncTask;

import kr.forpet.view.factory.HealthViewFactory;
import kr.forpet.view.factory.VaccineViewFactory;
import kr.forpet.view.factory.ViewFactory;
import kr.forpet.view.pet.model.PetModel;

public class PetPresenterImpl implements PetPresenter {

    private PetPresenter.View mView;
    private PetModel mPetModel;

    public PetPresenterImpl() {
        mPetModel = new PetModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mPetModel.loadAppDatabase(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadMeal() {

    }

    @Override
    public void loadVaccine() {
        new AsyncTask<Void, Void, ViewFactory>() {
            @Override
            protected ViewFactory doInBackground(Void... voids) {
                return new VaccineViewFactory(
                        mPetModel.getVaccineListByPetType("강아지"),
                        mPetModel.getVaccineListByPetType("고양이")
                );
            }

            @Override
            protected void onPostExecute(ViewFactory factory) {
                super.onPostExecute(factory);
                mView.updateView(factory);
            }
        }.execute();
    }

    @Override
    public void loadHealth() {
        new AsyncTask<Void, Void, ViewFactory>() {
            @Override
            protected ViewFactory doInBackground(Void... voids) {
                return new HealthViewFactory(
                        mPetModel.getEmergencyList(),
                        mPetModel.getCommonDiseaseList()
                );
            }

            @Override
            protected void onPostExecute(ViewFactory factory) {
                super.onPostExecute(factory);
                mView.updateView(factory);
            }
        }.execute();
    }
}
