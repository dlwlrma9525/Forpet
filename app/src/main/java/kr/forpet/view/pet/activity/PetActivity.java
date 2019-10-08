package kr.forpet.view.pet.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.forpet.view.factory.ViewFactory;
import kr.forpet.view.pet.presenter.PetPresenter;
import kr.forpet.view.pet.presenter.PetPresenterImpl;

public class PetActivity extends AppCompatActivity
        implements PetPresenter.View {

    public static final int TYPE_MEAL = 0;
    public static final int TYPE_VACCINE = 1;
    public static final int TYPE_HEALTH = 2;

    private PetPresenter mPetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_pet);

        mPetPresenter = new PetPresenterImpl();
        mPetPresenter.setView(this);
        mPetPresenter.onCreate(getApplicationContext());

        switch (getIntent().getIntExtra("type", 0)) {
            case TYPE_MEAL:
                mPetPresenter.loadMeal();
                break;
            case TYPE_VACCINE:
                mPetPresenter.loadVaccine();
                break;
            default:
                mPetPresenter.loadHealth();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPetPresenter.onDestroy();
    }

    @Override
    public void updateView(ViewFactory factory) {
        setContentView(factory.createView(this));
    }
}
