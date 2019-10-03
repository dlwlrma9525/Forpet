package kr.forpet.view.pet.presenter;

import android.content.Context;

import kr.forpet.view.factory.ViewFactory;

public interface PetPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    interface View {
        void updateView(ViewFactory factory);
    }
}
