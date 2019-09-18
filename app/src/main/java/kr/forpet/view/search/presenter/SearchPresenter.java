package kr.forpet.view.search.presenter;

import android.content.Context;

import java.util.List;

import kr.forpet.data.entity.Shop;

public interface SearchPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onSearchByName(String keyword, String categoryGroupCode);
    void onSearchByRegion(String keyword, String categoryGroupCode);

    interface View {

        void onResult(List<Shop> shopList);
    }
}
