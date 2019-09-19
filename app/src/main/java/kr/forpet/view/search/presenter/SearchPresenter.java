package kr.forpet.view.search.presenter;

import android.content.Context;

import java.util.List;

import kr.forpet.data.entity.Shop;

public interface SearchPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();

    void onSearchByName(String keyword);
    void onSearchByRegion(String keyword);
    void onItemClick(Shop shop);

    interface View {

        void updateItems(List<Shop> shopList);
        void showDetail(Shop shop);
    }
}
