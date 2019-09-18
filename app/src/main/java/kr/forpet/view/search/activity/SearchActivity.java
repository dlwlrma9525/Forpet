package kr.forpet.view.search.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.view.search.adapter.SearchRecyclerAdapter;
import kr.forpet.view.search.presenter.SearchPresenter;
import kr.forpet.view.search.presenter.SearchPresenterImpl;

public class SearchActivity extends AppCompatActivity
        implements SearchPresenter.View {

    @BindView(R.id.edit_text_search)
    EditText editTextSearch;

    @BindView(R.id.tab_layout_search)
    TabLayout tabLayoutSearch;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;

    private SearchPresenter mSearchPresenter;
    private String categoryGroupCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mSearchPresenter = new SearchPresenterImpl();
        mSearchPresenter.setView(this);
        mSearchPresenter.onCreate(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        categoryGroupCode = bundle.getString("extra");

        showSoftKeyboard();
        editTextSearch.requestFocus();
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    String keyword = s.toString();

                    switch (tabLayoutSearch.getSelectedTabPosition()) {
                        case 0:
                            mSearchPresenter.onSearchByName(keyword, categoryGroupCode);
                            break;
                        case 1:
                            mSearchPresenter.onSearchByRegion(keyword, categoryGroupCode);
                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tabLayoutSearch.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewSearch.setLayoutManager(manager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDestroy();
        hideSoftKeyboard();
    }

    @Override
    public void onResult(List<Shop> shopList) {
        recyclerViewSearch.setAdapter(new SearchRecyclerAdapter(shopList));
    }

    private void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
