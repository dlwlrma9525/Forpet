package kr.forpet.view.main.presenter;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;

    @Override
    public void setView(View view) {
        this.mView = view;
    }
}
