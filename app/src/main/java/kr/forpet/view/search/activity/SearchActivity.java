package kr.forpet.view.search.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.databinding.ActivitySearchBinding;
import kr.forpet.view.factory.BottomSheetItemFactory;
import kr.forpet.view.factory.ItemViewFactory;
import kr.forpet.view.search.adapter.SearchRecyclerAdapter;
import kr.forpet.view.search.presenter.SearchPresenter;
import kr.forpet.view.search.presenter.SearchPresenterImpl;

public class SearchActivity extends AppCompatActivity
        implements SearchPresenter.View {

    private ActivitySearchBinding mBinding;
    private SearchPresenter mSearchPresenter;
    private BottomSheetBehavior mPersistentBottomSheet;

    // https://developer.android.com/reference/android/databinding/ObservableArrayList
    // https://developer.android.com/reference/android/databinding/ObservableList.OnListChangedCallback.html
    private ObservableArrayList mObservableList;
    private SearchRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // A mBinding class is generated for each layout file. By default, the name of the class is based on the name of the layout file,
        // converting it to Pascal case and adding the Binding suffix to it.
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mObservableList = new ObservableArrayList<>();

        mBinding.setObservableList(mObservableList);
        mBinding.getRoot().setOnClickListener((v) -> hideSoftKeyboard());

        mSearchPresenter = new SearchPresenterImpl();
        mSearchPresenter.setView(this);
        mSearchPresenter.onCreate(getApplicationContext());

        showSoftKeyboard();
        mBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    String keyword = s.toString();

                    switch (mBinding.tabLayoutSearch.getSelectedTabPosition()) {
                        case 0:
                            mSearchPresenter.onSearchByName(keyword);
                            break;
                        case 1:
                            mSearchPresenter.onSearchByRegion(keyword);
                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.tabLayoutSearch.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String keyword = mBinding.editTextSearch.getText().toString();

                if (keyword.length() > 1) {
                    switch (tab.getPosition()) {
                        case 0:
                            mSearchPresenter.onSearchByName(keyword);
                            break;
                        case 1:
                            mSearchPresenter.onSearchByRegion(keyword);
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAdapter = new SearchRecyclerAdapter(mSearchPresenter);
        mBinding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerViewSearch.setAdapter(mAdapter);
        mBinding.layoutBottomSheet.setVisibility(View.VISIBLE);

        mPersistentBottomSheet = BottomSheetBehavior.from(mBinding.layoutBottomSheet);
        mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        mPersistentBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_SETTLING:
                        mBinding.layoutSheetEffect.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mBinding.layoutSheetEffect.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDestroy();
        hideSoftKeyboard();
    }

    @Override
    public void onBackPressed() {
        if (mPersistentBottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            mBinding.layoutSheetEffect.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void updateItems(List<Shop> shopList) {
        mObservableList.clear();
        mObservableList.addAll(shopList);
    }

    @Override
    public void showDetail(Shop shop) {
        ItemViewFactory factory = new BottomSheetItemFactory(shop);
        hideSoftKeyboard();

        mBinding.layoutBottomSheet.removeAllViews();
        mBinding.layoutBottomSheet.addView(factory.createView(getApplicationContext()));
        mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mBinding.editTextSearch, 0);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.editTextSearch.getWindowToken(), 0);
    }
}
