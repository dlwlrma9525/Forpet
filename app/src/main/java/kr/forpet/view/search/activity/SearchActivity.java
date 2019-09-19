package kr.forpet.view.search.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.databinding.ActivitySearchBinding;
import kr.forpet.view.search.adapter.SearchRecyclerAdapter;
import kr.forpet.view.search.presenter.SearchPresenter;
import kr.forpet.view.search.presenter.SearchPresenterImpl;

public class SearchActivity extends AppCompatActivity
        implements SearchPresenter.View {

    private ActivitySearchBinding mBinding;
    private SearchPresenter mSearchPresenter;

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

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAdapter = new SearchRecyclerAdapter();
        mBinding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerViewSearch.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDestroy();
        hideSoftKeyboard();
    }

    @Override
    public void updateResult(List<Shop> shopList) {
        mObservableList.clear();
        mObservableList.addAll(shopList);
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
